//
//  Model.swift
//  CheapTrip
//
//  Created by Shalopay on 27.04.2023.
//

import Foundation

struct Model {
    var isExpanded: Bool
    let namePoint: String
    let price: String
    let time: String
    let icons: [TypeTransfer]
    let transfer: [Transfer]
}

enum TypeTransfer: String, CaseIterable {
    case airplane = "Flight"
    case bus = "Bus"
    case train = "Train"
    case cardrive = "Car Drive"
    case taxi = "Taxi"
    case walk = "Walk"
    case towncar = "Town Car"
    case rideshare = "Ride Share"
    case shuttle = "Shuttle"
    case ferry = "Ferry"
    case subway = "Subway"
    var imageName: String {
        switch self {
        case .airplane:
            return "airplane"
        case .bus:
            return "bus.fill"
        case .train:
            return "bus.fill"
        case .cardrive:
            return "bus.fill"
        case .taxi:
            return "bus.fill"
        case .walk:
            return "bus.fill"
        case .towncar:
            return "bus.fill"
        case .rideshare:
            return "bus.fill"
        case .shuttle:
            return "bus.fill"
        case .ferry:
            return "bus.fill"
        case .subway:
            return "bus.fill"
        }
    }
}

struct Transfer {
    let name: String
    let type: TypeTransfer
    let time: String
    let price: String
}


struct Routes: Codable {
    var from, to, price, duration: Int
    var directRoutes: String
}

