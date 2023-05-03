//
//  Routes.swift
//  CheapTrip
//
//  Created by Shalopay on 30.04.2023.
//

import Foundation
struct Routess: Decodable {
    let from, to, price, duration: Int
    let uuid: String
    let directRoutes: [String]
    enum CodingKeys: String, CodingKey {
            case from, to, price, duration
            case directRoutes = "direct_routes"
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        
        from = try container.decode(Int.self, forKey: CodingKeys.from)
        to = try container.decode(Int.self, forKey: CodingKeys.to)
        price = try container.decode(Int.self, forKey: CodingKeys.price)
        duration = try container.decode(Int.self, forKey: CodingKeys.duration)
        directRoutes = try container.decode([String].self, forKey: CodingKeys.directRoutes)
        uuid = container.codingPath.first!.stringValue
    }
}
struct RoutessArray: Decodable {
    typealias RoutessArray = [Routess]
    private var array: RoutessArray
    private struct DynamicCodingKeys: CodingKey {
        var stringValue: String
        init?(stringValue: String) {
            self.stringValue = stringValue
        }
        var intValue: Int?
        init?(intValue: Int) {
            return nil
        }
    }
    init(from decoder: Decoder) throws {
            let container = try decoder.container(keyedBy: DynamicCodingKeys.self)
            var tempArray = RoutessArray()
            for key in container.allKeys {
                let decodedObject = try container.decode(Routess.self, forKey: DynamicCodingKeys(stringValue: key.stringValue)!)
                tempArray.append(decodedObject)
            }
            array = tempArray
        }
}

extension RoutessArray: Collection {
    typealias Index = RoutessArray.Index
    typealias Element = RoutessArray.Element
    
    var startIndex: Index { return array.startIndex }
    var endIndex: Index { return array.endIndex }
    
    subscript(index: Index) -> Iterator.Element {
        get { return array[index] }
    }
    func index(after i: Index) -> Index {
        return array.index(after: i)
    }
}

