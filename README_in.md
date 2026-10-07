# KMPStarter

अपने **Kotlin Multiplatform (KMP)** विकास की शुरुआत इस GitHub रिपॉजिटरी के साथ करें। [हृदय चंद्र दास](https://github.com/ihridoydas) द्वारा डिज़ाइन किया गया, यह Android, iOS, Desktop, Web (Wasm), Backend, TV, Wear OS, Automotive, और XR अनुप्रयोगों के लिए एक मजबूत आधार प्रदान करता है।

## KMPStarter क्यों?

- **डिफ़ॉल्ट रूप से मल्टीप्लेटफ़ॉर्म:** Android, iOS, Desktop और Web के लिए साझा लॉजिक और UI (Compose Multiplatform)।
- **यूनिवर्सल एडाप्टिव UI:** फोन, टैबलेट, डेस्कटॉप, वेब, टीवी, वियर ओएस, ऑटोमोटिव और XR के लिए विंडो साइज और `FormFactorMode` आधारित स्वचालित एडाप्टिव लेआउट।
- **Ktor बैकएंड और Swagger UI:** `/docs` पर इंटरैक्टिव Swagger UI दस्तावेज़ीकरण के साथ प्रोडक्शन-रेडी बैकएंड सर्वर (`:apps:serverApp`)।
- **स्मार्ट सेटअप और फॉर्म-फैक्टर टॉगल:** प्रोजेक्ट का नाम बदलने और फॉर्म-फैक्टर लाइब्रेरी टॉगल करने की स्क्रिप्ट (`useAndroidTV`, `useWearOS`, `useAndroidAutomotive`, `useAndroidXR`), जो ऑटोमैटिकली अप्रयुक्त सोर्स फाइल और डिपेंडेंसी हटा देती है।
- **बहुभाषी समर्थन:** अंग्रेजी, जापानी, बंगाली और सिस्टम डिफ़ॉल्ट के लिए इन-बिल्ट समर्थन।

## आर्किटेक्चर

यह प्रोजेक्ट **Clean Architecture** सिद्धांतों और **मॉड्यूलर मल्टीप्लेटफॉर्म लेयरिंग** रणनीति का पालन करता है:

```text
├── :apps               # प्लेटफॉर्म-विशिष्ट एंट्री पॉइंट (androidApp, desktopApp, webApp, serverApp)
├── iosApp              # iOS स्विफ्ट एप्लिकेशन रैपर (Xcode प्रोजेक्ट और Compose Multiplatform)
├── :feature            # पृथक फीचर मॉड्यूल (auth, home, profile, settings)
├── :navigation         # टाइप-सेफ Navigation3 API और कार्यान्वयन
└── :core               # फ़ाउंडेशन मॉड्यूल
    ├── :core:designsystem # Material 3 थीम, कंपोनेंट्स और एडाप्टिव फॉर्म-फैक्टर UI
    ├── :core:common       # यूटिलिटीज, LanguageManager और स्थिरांक
    ├── :core:model        # साझा डोमेन मॉडल और DTO
    ├── :core:network      # Ktor HTTP क्लाइंट और API सेवाएं
    ├── :core:database     # Room KMP डेटाबेस और DAO
    ├── :core:datastore    # टाइप-सेफ DataStore प्राथमिकताएं
    ├── :core:platform     # प्लेटफॉर्म-विशिष्ट expect/actual अमूर्तन
    └── :core:testing      # टेस्ट हार्नेस और मॉक्स
```

विस्तृत आर्किटेक्चर दिशानिर्देशों के लिए [प्रोडक्शन आर्किटेक्चर गाइड](documentation/architecture/KmpTemplate_Production_Architecture.md) पढ़ें।

## शुरुआत कैसे करें

1. अपने खाते में एक रिपॉजिटरी बनाने के लिए **"Use this template"** पर क्लिक करें।
2. `buildscripts/setup.gradle` खोलें और अपने प्रोजेक्ट का विवरण व फॉर्म-फैक्टर कॉन्फ़िगर करें:
    ```groovy
    def renameConfig = [
        newTemplateName          : "MyAwesomeApp",    // आपका प्रोजेक्ट नाम
        newTemplateAppId         : "com.example.app", // आपकी पैकेज आईडी
        newMaterialThemeName     : "AppTheme",        // आपका मटेरियल थीम नाम

        // लाइब्रेरी और फॉर्म-फैक्टर टॉगल करें
        useKoin                  : true,
        useKtor                  : true,
        useRoomKmp               : true,
        useComposeMultiplatform  : true,
        useAndroidXR             : true,
        useAndroidTV             : true,
        useWearOS                : true,
        useAndroidAutomotive     : true,
    ]
    ```
3. अपने टर्मिनल में सेटअप कमांड चलाएँ:
    ```bash
    ./gradlew renameTemplate
    ```
4. **Android Studio को पुनरारंभ करें**, Gradle को सिंक करें, और आप बिल्ड के लिए तैयार हैं!

## Ktor बैकएंड और Swagger UI चलाना

बैकएंड सर्वर शुरू करने के लिए चलाएँ:
```bash
./gradlew :apps:serverApp:run
```
इंटरैक्टिव Swagger UI देखने के लिए अपने ब्राउज़र में **[http://localhost:8080/docs](http://localhost:8080/docs)** खोलें।

## API दस्तावेज़ीकरण बनाना (Dokka)

सभी साझा लाइब्रेरीज़ और कोर मॉड्यूल के लिए एकीकृत मल्टी-मॉड्यूल API दस्तावेज़ीकरण बनाएँ:
```bash
./gradlew dokkaGenerate
```
बनाई गई दस्तावेज़ीकरण साइट को **`dokkaDocs/index.html`** फ़ाइल में देखें।
