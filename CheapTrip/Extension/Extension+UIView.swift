//
//  UIView+Extension.swift
//  BigBenApp
//
//  Created by Shalopay on 20.03.2023.
//

import UIKit
public extension UIView {
    static let screenWidth = UIScreen.main.bounds.size.width
    static let screenHeight = UIScreen.main.bounds.size.height
    
    func addSubviews(_ views: [UIView]) {
        for view in views {
            self.addSubview(view)
        }
    }
}
