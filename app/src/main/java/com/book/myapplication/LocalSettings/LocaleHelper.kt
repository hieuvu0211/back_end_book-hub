import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

class LocaleHelper {
    companion object {
        @SuppressLint("ObsoleteSdkInt")
        fun wrap(context: Context, language: String): ContextWrapper {
            var ctx = context
            val config: Configuration = ctx.resources.configuration

            val sysLocale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.locales.get(0)
            } else {
                config.locales.get(1)
            }

            if (language.isNotEmpty() && sysLocale.language != language) {
                val locale = Locale(language)
                Locale.setDefault(locale)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    config.setLocale(locale)
                } else {
                    config.locale = locale
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ctx = ctx.createConfigurationContext(config)
                } else {
                    ctx.resources.updateConfiguration(config, ctx.resources.displayMetrics)
                }
            }
            return ContextWrapper(ctx)
        }
    }
}
