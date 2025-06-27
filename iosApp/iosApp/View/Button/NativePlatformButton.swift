import SwiftUI

private struct NativePlatformButton: View {
    let action: () -> Void
    let text: String
    
    var body: some View {
        Button(action: action) {
            Text(text)
        }
    }
}

extension iOSNativeViewFactory {
    func createNativePlatformButton(onClick: @escaping () -> Void, text: String) -> UIViewController {
        UIHostingController(rootView: NativePlatformButton(action: onClick, text: text))
    }
}
