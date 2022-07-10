import analytics




struct CheckoutEvents{
    
    let CHECKOUT_START = "checkout_start"
    let CHECKOUT_ADDRESS_SELECTED = "checkout_address_selected"
    let CHECKOUT_PURCHASE = "checkout_purchase"
    let CHECKOUT_PURCHASE_2 = "checkout_purchase_2"

    
    let PRODUCT_NAME_PARAM = "product_name"
    let ADDRESS_PARAM = "address"
    let currency = "USD"

func StartCheckout(sum: Double, productName: String) ->  AnalyticsEvent{
        return Event.Builder(name: CHECKOUT_START)
            .withSum(amount: sum, currency: currency)
            .withParam(paramName: PRODUCT_NAME_PARAM, paramValue: productName)
            .build()
    }


func SelectAddress(address: String) ->  AnalyticsEvent{
    return Event.Builder(name: CHECKOUT_ADDRESS_SELECTED)
        .withParam(paramName: ADDRESS_PARAM, paramValue: address)
        .build()
    }



func Purchase(sum: Double, productName: String) -> Array<AnalyticsEvent>{
    var events = [AnalyticsEvent]()
    
    events.append(Event.Builder(name: CHECKOUT_PURCHASE)
        .withSum(amount: sum, currency: currency)
        .withParam(paramName: PRODUCT_NAME_PARAM, paramValue: productName)
        .build())
    
    events.append(Event.Builder(name: CHECKOUT_PURCHASE_2)
        .withSum(amount: sum, currency: currency)
        .withParam(paramName: PRODUCT_NAME_PARAM, paramValue: productName)
        .build())
    
    return events
    }
}


