package eu.artofcoding.app1.web.mbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@ManagedBean(name = "language")
@SessionScoped
public class LanguageBean implements Serializable {

    /**
     * Actual locale to use.
     */
    private String localeCode;

    /**
     * Supported countries/locales.
     */
    private static Map<String, Object> countries;

    static {
        countries = new LinkedHashMap<String, Object>();
        countries.put("English", Locale.ENGLISH); // Label, value
        countries.put("German", Locale.GERMAN);
    }

    /**
     * Return a list of countries/languages supported by this application.
     * @return
     */
    public Map<String, Object> getCountries() {
        return countries;
    }

    /**
     * Return actual locale code.
     * Asking for locale code for the first time we use locale from actual HTTP request.
     * @return String Locale code.
     */
    public String getLocaleCode() {
        if (null == localeCode) {
            localeCode = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale().getLanguage();
            System.out.println(this + ".getLocaleCode(): set locale (from request) to " + localeCode);
        }
        return localeCode;
    }

    /**
     * Set locale code.
     * @param localeCode
     */
    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    /**
     * Value change event listener.
     * @param e
     */
    public void countryLocaleCodeChanged(ValueChangeEvent e) {
        String newLocaleValue = e.getNewValue().toString();
        System.out.println(this + ".countryLocaleCodeChanged: " + newLocaleValue);
        // Loop country map to compare the locale code
        for (Map.Entry<String, Object> entry : countries.entrySet()) {
            final boolean foundLocale = entry.getValue().toString().equals(newLocaleValue);
            if (foundLocale) {
                // Change locale
                final Locale locale = (Locale) entry.getValue();
                FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
            }
        }
    }

    /**
     * Get a localized message from a bundle.
     * @param baseName
     * @param locale
     * @param key
     * @return String The message.
     */
    public String getMessage(String baseName, Locale locale, String key) {
        ResourceBundle res = ResourceBundle.getBundle(baseName, locale);
        return res.getString(key);
    }

}
