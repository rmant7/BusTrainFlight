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
    case Flight = "Flight"
    case Bus = "Bus"
    case Train = "Train"
    case Cardrive = "Car Drive"
    case Taxi = "Taxi"
    case Walk = "Walk"
    case Towncar = "Town Car"
    case Rideshare = "Ride Share"
    case Shuttle = "Shuttle"
    case Ferry = "Ferry"
    case Subway = "Subway"
    
    
    
    var imageName: String {
        switch self {
        case .Flight:
            return "airplane"
        case .Bus:
            return "bus.fill"
        case .Train:
            return "bus.fill"
        case .Cardrive:
            return "bus.fill"
        case .Taxi:
            return "bus.fill"
        case .Walk:
            return "bus.fill"
        case .Towncar:
            return "bus.fill"
        case .Rideshare:
            return "bus.fill"
        case .Shuttle:
            return "bus.fill"
        case .Ferry:
            return "bus.fill"
        case .Subway:
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

