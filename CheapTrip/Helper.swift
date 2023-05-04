//
//  Helper.swift
//  CheapTrip
//
//  Created by Shalopay on 27.04.2023.
//

import UIKit

enum Helper {
//    static var trips: [Model] = [
//        Model(isExpanded: true, namePoint: "Kyoto > Aizawl > Agartala > Allahabad > Jaipur > Krakow > Katowice > Dortmund > Frankfurt", price: "€ 233.00", time: "3d 19h 21min", icons: [TypeTransfer.airplane, TypeTransfer.subway, TypeTransfer.bus, TypeTransfer.airplane, TypeTransfer.bus, TypeTransfer.bus, TypeTransfer.airplane, TypeTransfer.bus], transfer: [
//            Transfer(name: "Kyoto > Aizawl", type: TypeTransfer.airplane, time: "1h 10min", price: "€ 133.00"),
//            Transfer(name: "Aizawl > Agartala", type: TypeTransfer.bus, time: "1h 10min", price: "€ 133.00"),
//            Transfer(name: "Agartala > Allahabad", type: TypeTransfer.bus, time: "1h 10min", price: "€ 133.00"),
//            Transfer(name: "Allahabad > Jaipur", type: TypeTransfer.airplane, time: "1h 10min", price: "€ 133.00"),
//            Transfer(name: "Jaipur > Krakow", type: TypeTransfer.bus, time: "1h 10min", price: "€ 133.00"),
//            Transfer(name: "Krakow > Katowice", type: TypeTransfer.bus, time: "1h 10min", price: "€ 133.00"),
//            Transfer(name: "Katowice > Dortmund", type: TypeTransfer.airplane, time: "1h 10min", price: "€ 133.00"),
//            Transfer(name: "Dortmund > Frankfurt", type: TypeTransfer.bus, time: "1h 10min", price: "€ 133.00")
//        ]),
//        Model(isExpanded: true, namePoint: "Kyoto > Aizawl > Agartala > Allahabad", price: "€ 500.00", time: "10d 19h 21min", icons: [TypeTransfer.airplane, TypeTransfer.bus, TypeTransfer.airplane], transfer: [
//            Transfer(name: "Kyoto > Aizawl", type: TypeTransfer.airplane, time: "1h 10min", price: "€ 100.00"),
//            Transfer(name: "Aizawl > Agartala", type: TypeTransfer.bus, time: "1h 10min", price: "€ 100.00"),
//            Transfer(name: "Agartala > Allahabad", type: TypeTransfer.airplane, time: "1h 10min", price: "€ 100.00"),
//        ]),
//        Model(isExpanded: true, namePoint: "Allahabad > Jaipur > Krakow > Katowice > Dortmund > Frankfurt", price: "€ 000.00", time: "00d 00h 00min", icons: [ TypeTransfer.bus,
//            TypeTransfer.bus,
//            TypeTransfer.bus,
//            TypeTransfer.airplane,
//            TypeTransfer.bus],
//              transfer: [
//                Transfer(name: "Allahabad > Jaipur", type: TypeTransfer.bus, time: "1h 10min", price: "€ 111.00"),
//                Transfer(name: "Jaipur > Krakow", type: TypeTransfer.bus, time: "1h 10min", price: "€ 222.00"),
//                Transfer(name: "Krakow > Katowice", type: TypeTransfer.bus, time: "1h 10min", price: "€ 333.00"),
//                Transfer(name: "Katowice > Dortmund", type: TypeTransfer.airplane, time: "1h 10min", price: "€ 444.00"),
//                Transfer(name: "Dortmund > Frankfurt", type: TypeTransfer.bus, time: "1h 10min", price: "€ 555.00"),
//              ])
//    ]
    
    enum Color {
        enum ViewController {
            static var whiteColor = UIColor(hexRGB: "#FFFFFF") ?? UIColor()
            static var blackColor = UIColor(hexRGB: "#000000") ?? UIColor()
        }
        enum NavigatorBar {
            static var redColor = UIColor(hexRGB: "#FF5722") ?? UIColor()
        }
    }
    
    enum Fonts {
        static func Medium(size: CGFloat) -> UIFont {
            UIFont.systemFont(ofSize: size, weight: .medium)
        }
        static func SemiBold(size: CGFloat) -> UIFont {
            UIFont.systemFont(ofSize: size, weight: .semibold)
        }
        
        static func Regular(size: CGFloat) -> UIFont {
            UIFont.systemFont(ofSize: size, weight: .regular)
        }
    }
}

enum Current {
    static var CurrentTransportType = [TransportType]()
    static var AllLocations = [LocationsType]()
    static var AllDirectRoutes = [DirectRoutes]()
    static var AllRoutes = [Routess]()
    static var AllFixedRoutes = [Routess]()
    static var AllFlyingRoutes = [Routess]()
}

public func setupName(_ points: [String]) -> NSMutableAttributedString {
    let completeText = NSMutableAttributedString(string: "")
    points.forEach { point in
        let attachmentString = NSAttributedString(string: point)
        let emptyattachmentString = NSAttributedString(string: " > ")
        completeText.append(attachmentString)
        completeText.append(emptyattachmentString)
    }
    completeText.deleteCharacters(in: NSRange(location: (completeText.length) - 3, length: 3))
   return completeText
}

public func removeDuplicates(array: [String]) -> [String] {
    var result = [String]()
    for value in array {
        if !result.contains(where: { $0 == value }) {
            result.append(value)
        }
    }
    return result
}

public func getIconTrasport(type: Int) -> String {
    guard let transport = Current.CurrentTransportType.first(where: {$0.uuid == String(type)}), let icon = TypeTransfer(rawValue: transport.name) else {return "ERROR :\(#function)"}
    return icon.imageName
}

public func getNamePoint(_ id: Int) -> String {
    guard let namePoint = Current.AllLocations.first(where:{ $0.uuid == String(id)
    }) else {return "ERROR: \(#function)"}
    return namePoint.name
}

