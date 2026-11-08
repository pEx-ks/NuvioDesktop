package com.nuvio.app.features.home

private val arabicLanguageNames = setOf("ar", "ara", "arabic", "العربية", "عربي", "عربية")

private val arabicProductionCountries = setOf(
    "sa", "sau", "saudi arabia", "السعودية", "المملكة العربية السعودية",
    "ae", "are", "united arab emirates", "uae", "الإمارات", "الإمارات العربية المتحدة",
    "bh", "bhr", "bahrain", "البحرين",
    "kw", "kwt", "kuwait", "الكويت",
    "om", "omn", "oman", "عمان", "سلطنة عمان",
    "qa", "qat", "qatar", "قطر",
    "dz", "dza", "algeria", "الجزائر",
    "eg", "egy", "egypt", "مصر",
    "iq", "irq", "iraq", "العراق",
    "jo", "jor", "jordan", "الأردن",
    "lb", "lbn", "lebanon", "لبنان",
    "ly", "lby", "libya", "ليبيا",
    "ma", "mar", "morocco", "المغرب",
    "mr", "mrt", "mauritania", "موريتانيا",
    "ps", "pse", "palestine", "فلسطين",
    "sd", "sdn", "sudan", "السودان",
    "sy", "syr", "syria", "سوريا",
    "tn", "tun", "tunisia", "تونس",
    "ye", "yem", "yemen", "اليمن",
)

internal fun isArabicOrGulfMedia(language: String?, productionCountries: List<String> = emptyList()): Boolean {
    val originalLanguage = language?.trim()?.lowercase()?.substringBefore('-')?.substringBefore('_')
    return originalLanguage in arabicLanguageNames ||
        productionCountries.any { country ->
            country.split(',', '/').any { it.trim().lowercase() in gulfProductionCountries }
        }
}
