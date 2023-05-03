//
//  Extension+Int.swift
//  CheapTrip
//
//  Created by Shalopay on 02.05.2023.
//

import Foundation
extension Int {
    func getDay() -> String {
        let formatter = DateComponentsFormatter()
        formatter.allowedUnits = [.day, .hour, .minute]
        formatter.unitsStyle = .brief
        return formatter.string(from: TimeInterval(self))!
    }
}
