//
//  CustomButton.swift
//  FinalDiplomNetology
//
//  Created by Shalopay on 27.01.2023.
//

import UIKit
class CustomButton: UIButton {
    let paragraphStyle: NSMutableParagraphStyle = NSMutableParagraphStyle()
    init(title: String, font: UIFont, backGroundColor: UIColor, textColor: UIColor, cornerRadius: CGFloat, lineHeightMultiple: CGFloat, kern: Double) {
        super.init(frame: .zero)
        titleLabel?.font = font
        backgroundColor = backGroundColor
        titleLabel?.textColor = textColor
        layer.cornerRadius = cornerRadius
        paragraphStyle.lineHeightMultiple = lineHeightMultiple
        setAttributedTitle(NSAttributedString(string: title, attributes: [NSAttributedString.Key.paragraphStyle : paragraphStyle, NSAttributedString.Key.kern: kern]), for: .normal)
        clipsToBounds = true
        translatesAutoresizingMaskIntoConstraints = false
    }
    
    func animationPressButton() {
        UIView.animate(withDuration: 0.15, delay: 0, usingSpringWithDamping: 0.2, initialSpringVelocity: 0.5, options: .curveEaseIn, animations: {
            self.transform = CGAffineTransform(scaleX: 0.92, y: 0.92)
        }) { (_) in
            UIView.animate(withDuration: 0.15, delay: 0, usingSpringWithDamping: 0.4, initialSpringVelocity: 2, options: .curveEaseIn, animations: {
                self.transform = CGAffineTransform(scaleX: 1, y: 1)
            }, completion: nil)
        }
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
