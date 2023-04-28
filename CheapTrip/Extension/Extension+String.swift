//
//  Extension+String.swift
//  FinalDiplomNetology
//
//  Created by Shalopay on 29.01.2023.
//

import UIKit
extension String {
    func getHeight() -> CGFloat {
        let constraintRect = CGSize(width: CGFloat(0), height: .greatestFiniteMagnitude)
        let boundingBox = self.boundingRect(with: constraintRect, options: .usesLineFragmentOrigin, attributes: [NSAttributedString.Key.font: UIFont(name: "rubik-regular", size: 12)!], context: nil)
        return ceil(boundingBox.height)
    }
    func getWidth() -> CGFloat {
        let constraintRect = CGSize(width: .greatestFiniteMagnitude, height: CGFloat(0))
        let boundingBox = self.boundingRect(with: constraintRect, options: .usesLineFragmentOrigin, attributes: [NSAttributedString.Key.font: UIFont(name: "rubik-regular", size: 12)!], context: nil)
        return ceil(boundingBox.width)
    }
}
