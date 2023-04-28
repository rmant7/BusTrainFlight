//
//  CustomTextField.swift
//  FinalDiplomNetology
//
//  Created by Shalopay on 27.01.2023.
//

import UIKit
class CustomTextField: UITextField {
    init(holder: String, colorText: UIColor, fontName: UIFont, cornerRadius: CGFloat) {
        super.init(frame: .zero)
         placeholder = holder
         textColor = colorText
        leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10, height: self.frame.height))
        leftViewMode = .always
         clipsToBounds = true
        font = fontName
         layer.cornerRadius = cornerRadius
         textAlignment = .center
         translatesAutoresizingMaskIntoConstraints = false
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
