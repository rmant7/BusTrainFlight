//
//  DownloadManager.swift
//  CheapTrip
//
//  Created by Shalopay on 28.04.2023.
//

import Foundation

class DownloadManager {
    
    //MARK: GetTransportType
    func getTransport(completionHandler: ((_ result: [TransportType]?)-> Void)?) {
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
}
