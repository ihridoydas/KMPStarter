# KMPStarter

अपने **Kotlin Multiplatform (KMP)** विकास की शुरुआत इस GitHub रिपॉजिटरी के साथ करें। [हृदय चंद्र दास](https://github.com/ihridoydas) द्वारा डिज़ाइन किया गया, यह Android, iOS, Desktop, Web (Wasm), Backend, TV, Wear OS, Automotive, और XR अनुप्रयोगों के लिए एक मजबूत आधार प्रदान करता है।

## KMPStarter क्यों?

- **डिफ़ॉल्ट रूप से मल्टीप्लेटफ़ॉर्म:** Android, iOS, Desktop और Web के लिए साझा लॉजिक और UI (Compose Multiplatform)।
- **यूनिवर्सल एडाप्टिव UI:** फोन, टैबलेट, डेस्कटॉप, वेब, टीवी, वियर ओएस, ऑटोमोटिव और XR के लिए विंडो साइज और `FormFactorMode` आधारित स्वचालित एडाप्टिव लेआउट।
- **Ktor बैकएंड और Swagger UI:** `/docs` पर इंटरैक्टिव Swagger UI दस्तावेज़ीकरण के साथ प्रोडक्शन-रेडी बैकएंड सर्वर (`:apps:serverApp`)।
- **स्मार्ट सेटअप और फॉर्म-फैक्टर टॉगल:** प्रोजेक्ट का नाम बदलने और फॉर्म-फैक्टर लाइब्रेरी टॉगल करने की स्क्रिप्ट (`useAndroidTV`, `useWearOS`, `useAndroidAutomotive`, `useAndroidXR`), जो ऑटोमैटिकली अप्रयुक्त सोर्स फाइल और डिपेंडेंसी हटा देती है।
- **बहुभाषी समर्थन:** अंग्रेजी, जापानी, बंगाली और सिस्टम डिफ़ॉल्ट के लिए इन-बिल्ट समर्थन।

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

## प्रोजेक्ट संरचना

- `:apps:androidApp`: एंड्रॉइड एप्लिकेशन मॉड्यूल।
- `:apps:desktopApp`: डेस्कटॉप एप्लिकेशन मॉड्यूल।
- `:apps:webApp`: वेब एप्लिकेशन मॉड्यूल।
- `:apps:serverApp`: Swagger UI के साथ Ktor बैकएंड सर्वर।
- `:core:designsystem`: साझा Material 3 डिज़ाइन सिस्टम, एडाप्टिव UI और फॉर्म-फैक्टर स्क्रीन।
- `:core:common`: साझा यूटिलिटीज और लैंग्वेज मैनेजर।
- `:core:model` & `:core:network`: साझा DTO, मॉडल और Ktor क्ला라이언트।
- `:navigation`: साझा नेविगेशन कॉन्फ़िगरेशन।
