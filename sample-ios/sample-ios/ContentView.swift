import SwiftUI
import analytics

struct ContentView: View {

    let analytics = AnalyticsProvider.analytics
    var body: some View {
    
      VStack {
        Button("Start checkout", action: {
            analytics.track(event: CheckoutEvents().StartCheckout(sum: 12.9, productName: "ложка"))
            })
           .buttonStyle(MyButton())

           Button("Address selected", action: {
            analytics.track(event: CheckoutEvents().SelectAddress(address: "Pushkin street 1"))
           })
           .buttonStyle(MyButton())

           Button("Purchase", action: {
            analytics.track(events: CheckoutEvents().Purchase(sum: 12.9, productName: "пицца"))
           })
           .buttonStyle(MyButton())

      }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}


struct MyButton: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding()
            .background(Color.blue)
            .foregroundColor(.white)
            .clipShape(Capsule())
            .scaleEffect(configuration.isPressed ? 1.2 : 1)
            .animation(.easeOut(duration: 0.2))
    }
}
