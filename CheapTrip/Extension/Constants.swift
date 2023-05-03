//
//  Constants.swift
//  FinalDiplomNetology
//
//  Created by Shalopay on 27.01.2023.
//

import UIKit
enum Resources {
    
    enum Color {
        enum ViewControlle {
            static var backGroundColorView = UIColor(hexRGB: "#E5E5E5") ?? UIColor()
        }
        enum Button {
            static var buttonBackGroundBlackColor = UIColor(hexRGB: "#2B3940") ?? UIColor()
        }
        enum Label {
            static var textBlackColor = UIColor(hexRGB: "#1F1E1E") ?? UIColor()
            static var textOrangeColor = UIColor(hexRGB: "#F69707") ?? UIColor()
            static var textWhiteColor = UIColor(hexRGB: "#FFFFFF") ?? UIColor()
            static var textLightColor = UIColor(hexRGB: "#D9D9D9") ?? UIColor()
            static var textDescriptionColor = UIColor(hexRGB: "#7E8183") ?? UIColor()
            static var textClearColor = UIColor.clear
        }
    }
    enum Fonts {
        static func rubikMedium(size: CGFloat) -> UIFont {
            UIFont.systemFont(ofSize: size, weight: .medium)
        }
        static func rubikSemiBold(size: CGFloat) -> UIFont {
            UIFont.systemFont(ofSize: size, weight: .semibold)
        }
        
        static func rubikRegular(size: CGFloat) -> UIFont {
            UIFont.systemFont(ofSize: size, weight: .regular)
        }
    }
    
    enum SettingProductItems: String, CaseIterable {
        case productImage = "Картинка"
        case productName = "Название"
        case productDescription = "Описание"
    }
    
    static var rulesPictures: [String] = ["rules1", "rules2"]
    
    static func getTitleView() -> UIImageView {
        let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: 39, height: 39))
            imageView.contentMode = .scaleAspectFit
            let image = UIImage(named: "logoBigBen")
            imageView.image = image
        return imageView
    }
}

func transliterate(nonLatin: String) -> String {
    return nonLatin
        .applyingTransform(.toLatin, reverse: false)?
        .applyingTransform(.stripDiacritics, reverse: false)?
        .lowercased()
        .replacingOccurrences(of: " ", with: "_") ?? nonLatin
}


enum Current {
    static var CurrentTransportType = [TransportType]()
    static var AllLocations = [LocationsType]()
}
