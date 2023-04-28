//
//  CustomLabel.swift
//  DiplomWeather
//
//  Created by Shalopay on 12.01.2023.
//

import UIKit
class CustomLabel: UILabel {
    let paragraphStyle: NSMutableParagraphStyle = NSMutableParagraphStyle()
    init(text: String, fontName: UIFont, colorText: UIColor, lineHeightMultiple: CGFloat, kern: Double) {
        super.init(frame: .zero)
        textColor = colorText
        font = fontName
        
        paragraphStyle.lineHeightMultiple = lineHeightMultiple
        attributedText = NSAttributedString(string: text, attributes: [NSAttributedString.Key.paragraphStyle : paragraphStyle, NSAttributedString.Key.kern: kern])
        self.translatesAutoresizingMaskIntoConstraints = false
    }
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
