# KMPStarter

আপনার **Kotlin Multiplatform (KMP)** উন্নয়ন এই GitHub রিপোজিটরি দিয়ে শুরু করুন। [হৃদয় চন্দ্র দাস](https://github.com/ihridoydas) এর ডিজাইন করা এই প্রজেক্টটি Android, iOS, Desktop, Web (Wasm), Backend, TV, Wear OS, Automotive, এবং XR অ্যাপ্লিকেশনের জন্য একটি শক্তিশালী ভিত্তি প্রদান করে।

## কেন KMPStarter?

- **ডিফল্টভাবে মাল্টিপ্ল্যাটফর্ম:** Android, iOS, Desktop এবং Web-এর জন্য শেয়ার্ড লজিক এবং UI (Compose Multiplatform)।
- **ইউনিভার্সাল অ্যাডাপ্টিভ UI:** ফোন, ট্যাবলেট, ফোল্ডেবল, ডেস্কটপ, ওয়েব, টিভি, ওয়্যার ওএস, অটোমোটিভ এবং এক্সআর-এর জন্য উইন্ডো সাইজ এবং `FormFactorMode` ভিত্তিক স্বয়ংক্রিয় অ্যাডাপ্টিভ লেআউট।
- **Ktor ব্যাকএন্ড ও Swagger UI:** `/docs`-এ ইন্টারেক্টিভ Swagger UI ডকুমেন্টেশনসহ প্রোডাকশন-রেডি ব্যাকএন্ড সার্ভার (`:apps:serverApp`)।
- **স্মার্ট সেটআপ ও ফর্ম-ফ্যাক্টর টগল:** প্রজেক্ট রিনেম করার এবং ফর্ম-ফ্যাক্টর লাইব্রেরি টগল করার স্ক্রিপ্ট (`useAndroidTV`, `useWearOS`, `useAndroidAutomotive`, `useAndroidXR`), যা অটোমেটিক্যালি অদরকারী সোর্স ফাইল ও ডিপেন্ডেন্সি রিমूव করে।
- **বহুভাষিক লোকলাইজেশন:** ইংরেজি, জাপানিজ, বাঙালি এবং সিস্টেম ডিফল্ট ভাষার বিল্ট-ইন সাপোর্ট।

## কিভাবে শুরু করবেন?

১. আপনার অ্যাকাউন্টে একটি রিপোজিটরি তৈরি করতে **"Use this template"** ক্লিক করুন।
২. `buildscripts/setup.gradle` ফাইলটি খুলুন এবং আপনার প্রজেক্টের তথ্য ও ফর্ম-ফ্যাক্টর কনফিগার করুন:
    ```groovy
    def renameConfig = [
        newTemplateName          : "MyAwesomeApp",    // আপনার প্রজেক্টের নাম
        newTemplateAppId         : "com.example.app", // আপনার প্যাকেজ আইডি
        newMaterialThemeName     : "AppTheme",        // আপনার মেটেরিয়াল থিমের নাম

        // লাইব্রেরি ও ফর্ম-ফ্যাক্টর টগল করুন
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
৩. আপনার টার্মিনালে সেটআপ কমান্ডটি রান করুন:
    ```bash
    ./gradlew renameTemplate
    ```
৪. **Android Studio রিস্টার্ট করুন**, গ্রেডল সিঙ্ক করুন এবং আপনি বিল্ড করার জন্য প্রস্তুত!

## Ktor ব্যাকএন্ড এবং Swagger UI চালানো

ব্যাকএন্ড সার্ভার স্টার্ট করতে রান করুন:
```bash
./gradlew :apps:serverApp:run
```
ব্রাউজারে **[http://localhost:8080/docs](http://localhost:8080/docs)** ভিজিট করুন ইন্টারেক্টিভ Swagger UI দেখার জন্য।

## প্রজেক্ট স্ট্রাকচার

- `:apps:androidApp`: অ্যান্ড্রয়েড অ্যাপ্লিকেশন মডিউল।
- `:apps:desktopApp`: ডেস্কটপ অ্যাপ্লিকেশন মডিউল।
- `:apps:webApp`: ওয়েব অ্যাপ্লিকেশন মডিউল।
- `:apps:serverApp`: Swagger UI সহ Ktor ব্যাকএন্ড সার্ভার।
- `:core:designsystem`: শেয়ার্ড Material 3 ডিজাইন সিস্টেম, অ্যাডাপ্টিভ UI এবং ফর্ম-ফ্যাক্টর স্ক্রিনসমূহ।
- `:core:common`: শেয়ার্ড ইউটিলিটি এবং ল্যাঙ্গুয়েজ ম্যানেজার।
- `:core:model` & `:core:network`: শেয়ার্ড DTO, মডেল এবং Ktor ক্লা라이언트।
- `:navigation`: শেয়ার্ড নেভিগেশন কনফিগারেশন।
