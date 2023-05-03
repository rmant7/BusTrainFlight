//
//  DirectRoutes.swift
//  CheapTrip
//
//  Created by Shalopay on 30.04.2023.
//

import Foundation
struct DirectRoutes: Decodable {
    let uuid: String
    let from, to, transport, price, duration: Int
    
    enum CodingKeys: String, CodingKey {
            case from, to, transport, price, duration
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        from = try container.decode(Int.self, forKey: CodingKeys.from)
        to = try container.decode(Int.self, forKey: CodingKeys.to)
        transport = try container.decode(Int.self, forKey: CodingKeys.transport)
        price = try container.decode(Int.self, forKey: CodingKeys.price)
        duration = try container.decode(Int.self, forKey: CodingKeys.duration)
        uuid = container.codingPath.first!.stringValue
    }
}

struct DirectRoutesArray: Decodable {
    typealias DirectRoutesArray = [DirectRoutes]
    private var array: DirectRoutesArray
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
            var tempArray = DirectRoutesArray()
            for key in container.allKeys {
                let decodedObject = try container.decode(DirectRoutes.self, forKey: DynamicCodingKeys(stringValue: key.stringValue)!)
                tempArray.append(decodedObject)
            }
            array = tempArray
        }
}

extension DirectRoutesArray: Collection {
    typealias Index = DirectRoutesArray.Index
    typealias Element = DirectRoutesArray.Element
    
    var startIndex: Index { return array.startIndex }
    var endIndex: Index { return array.endIndex }
    
    subscript(index: Index) -> Iterator.Element {
        get { return array[index] }
    }
    func index(after i: Index) -> Index {
        return array.index(after: i)
    }
}

