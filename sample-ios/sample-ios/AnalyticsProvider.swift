import analytics



class AnalyticsProvider {
    
    static var analytics: Analytics = {
        return Analytics.Builder()
            .addConsumer(consumer: FooConsumer())
            .addConsumerInterceptor(consumerInterceptor: FooInterceptor())
            .addInterceptor(interceptor: CommonInterceptor())
            .setExceptionHandler(exceptionHandler: { throwable in print("error" + throwable.message!) })
            .setDebugLog(shouldEnable: true)
            .build()
    }()
}

class CommonInterceptor: Interceptor{
    
    func intercept(event: Event, completionHandler: @escaping (Event?, Error?) -> Void) {
        
        let outParams = (event.params as NSDictionary).mutableCopy() as! NSMutableDictionary
        outParams.addEntries(from: ["deviceId":"ID", "userId": "ID"])
        return completionHandler(Event(event: event.event, sum: event.sum,
                                       params: outParams as! [String: Any]), nil);
    }
}

class FooInterceptor: ConsumerInterceptor{
    func intercept(consumer: AnalyticsConsumer, event: Event, completionHandler: @escaping (Event?, Error?) -> Void) {
        
        let outParams = (event.params as NSDictionary).mutableCopy() as! NSMutableDictionary
        if(consumer is FooConsumer){
            outParams.addEntries(from: ["fooUserId":"ID"])
        }
        return completionHandler( Event(event: event.event, sum: event.sum,
                                        params: outParams as! [String: Any]) , nil);
    }
}


class FooConsumer : AnalyticsConsumer {
    func canAccept(event: String) -> Bool {
        return true
    }
    
    func acceptEvent(event: AnalyticsEvent) {
        print("🚀 " + String(describing: type(of: self)) + " event accepted")
    }
}

