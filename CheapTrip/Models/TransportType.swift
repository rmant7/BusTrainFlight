//
//  TransportType.swift
//  CheapTrip
//
//  Created by Shalopay on 28.04.2023.
//

import Foundation

struct TransportType: Decodable {
    let name: String
    let id: String
    
    enum CodingKeys: CodingKey {
            case name
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        name = try container.decode(String.self, forKey: CodingKeys.name)
        id = container.codingPath.first!.stringValue
    }
}

struct TransportArray: Decodable {
    typealias TransportArrayType = [TransportType]
    private var array: TransportArrayType
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
            var tempArray = TransportArrayType()
            for key in container.allKeys {
                let decodedObject = try container.decode(TransportType.self, forKey: DynamicCodingKeys(stringValue: key.stringValue)!)
                tempArray.append(decodedObject)
            }
            array = tempArray
        }
}

extension TransportArray: Collection {
    typealias Index = TransportArrayType.Index
    typealias Element = TransportArrayType.Element
    
    var startIndex: Index { return array.startIndex }
    var endIndex: Index { return array.endIndex }
    
    subscript(index: Index) -> Iterator.Element {
        get { return array[index] }
    }
    func index(after i: Index) -> Index {
        return array.index(after: i)
    }
}
