//
//  DownloadManager.swift
//  CheapTrip
//
//  Created by Shalopay on 28.04.2023.
//

import Foundation

class DownloadManager {
    
    //MARK: GetTransportType
    public func getTransport(completionHandler: ((_ result: [TransportType]?)-> Void)?) {
        var transportType = [TransportType]()
        guard let path = Bundle.main.url(forResource: "transport", withExtension: "json") else { return print("not found transport.json") }
        do {
            let data = try Data(contentsOf: path)
            let result = try JSONDecoder().decode(TransportArray.self, from: data)
            transportType.append(contentsOf: result)
            completionHandler?(transportType)
        } catch {
            print("ERORR :\(error)")
            completionHandler?(nil)
        }
    }
    
    //MARK: GetLocationsType
    public func getLocations(completionHandler: ((_ result: [LocationsType]?)-> Void)?) {
        var locationsType = [LocationsType]()
        guard let path = Bundle.main.url(forResource: "locations", withExtension: "json") else { return print("not found locations.json") }
        do {
            let data = try Data(contentsOf: path)
            let result = try JSONDecoder().decode(LocationsArray.self, from: data)
            locationsType.append(contentsOf: result)
            completionHandler?(locationsType)
        } catch {
            print("ERORR :\(error)")
            completionHandler?(nil)
        }
    }
    //MARK: GetDirectRoutes
    public func getDirectRoutes(completionHandler: ((_ result: [DirectRoutes]?)-> Void)?) {
        var directRoutes = [DirectRoutes]()
        guard let path = Bundle.main.url(forResource: "direct_routes", withExtension: "json") else { return print("not found direct_routes.json") }
        do {
            let data = try Data(contentsOf: path)
            let result = try JSONDecoder().decode(DirectRoutesArray.self, from: data)
            directRoutes.append(contentsOf: result)
            completionHandler?(directRoutes)
        } catch {
            print("ERORR :\(error)")
            completionHandler?(nil)
        }
    }
    
    //MARK: GetRoutes
    public func getRoutes(completionHandler: ((_ result: [Routess]?)-> Void)?) {
        var routes = [Routess]()
        let paths = [
            Bundle.main.url(forResource: "routes", withExtension: "json"),
            Bundle.main.url(forResource: "fixed_routes", withExtension: "json"),
            Bundle.main.url(forResource: "flying_routes", withExtension: "json"),
            //Bundle.main.url(forResource: "direct_routes", withExtension: "json"),
        ]
        
        do {
            for path in paths {
                let data =  try Data(contentsOf: path!)
                let result = try JSONDecoder().decode(RoutessArray.self, from: data)
                routes.append(contentsOf: result)
            }
            completionHandler?(routes)
        } catch {
            print("ERORR :\(error)")
            completionHandler?(nil)
        }
    }
}
