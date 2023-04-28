//
//  Extension+UIViewController.swift
//  BigBenApp
//
//  Created by Shalopay on 08.04.2023.
//

import UIKit
extension UIViewController {
    func dismissTo(vc: UIViewController?, count: Int?, animated: Bool, completion: (() -> Void)? = nil) {
            var loopCount = 0
            var dummyVC: UIViewController? = self
            for _ in 0..<(count ?? 100) {
                loopCount = loopCount + 1
                dummyVC = dummyVC?.presentingViewController
                if let dismissToVC = vc {
                    if dummyVC != nil && dummyVC!.isKind(of: dismissToVC.classForCoder) {
                        dummyVC?.dismiss(animated: animated, completion: completion)
                    }
                }
            }
            if count != nil {
                dummyVC?.dismiss(animated: animated, completion: completion)
            }
        }
}

