import SwiftUI
import navigationImpl

@main
struct iOSApp: App {
    init() {
        AppKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
