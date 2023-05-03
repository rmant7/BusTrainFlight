//
//  LocationsType.swift
//  CheapTrip
//
//  Created by Shalopay on 30.04.2023.
//

import Foundation
struct LocationsType: Decodable {
    let uuid, name, countryName: String
    let latitude, longitude: Double
    
    enum CodingKeys: String, CodingKey {
            case name, latitude, longitude
            case countryName = "country_name"
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        
        name = try container.decode(String.self, forKey: CodingKeys.name)
        countryName = try container.decode(String.self, forKey: CodingKeys.countryName)
        uuid = container.codingPath.first!.stringValue
        latitude = try container.decode(Double.self, forKey: CodingKeys.latitude)
        longitude = try container.decode(Double.self, forKey: CodingKeys.longitude)
    }
}

struct LocationsArray: Decodable {
    typealias LocationsArrayType = [LocationsType]
    private var array: LocationsArrayType
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
            var tempArray = LocationsArrayType()
            for key in container.allKeys {
                let decodedObject = try container.decode(LocationsType.self, forKey: DynamicCodingKeys(stringValue: key.stringValue)!)
                tempArray.append(decodedObject)
            }
            array = tempArray
        }
}

extension LocationsArray: Collection {
    typealias Index = LocationsArrayType.Index
    typealias Element = LocationsArrayType.Element
    
    var startIndex: Index { return array.startIndex }
    var endIndex: Index { return array.endIndex }
    
    subscript(index: Index) -> Iterator.Element {
        get { return array[index] }
    }
    func index(after i: Index) -> Index {
        return array.index(after: i)
    }
}

