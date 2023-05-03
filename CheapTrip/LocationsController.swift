//
//  LocationsController.swift
//  CheapTrip
//
//  Created by Shalopay on 30.04.2023.
//

import UIKit

protocol LocationsDelegate: AnyObject {
    func getLocation(type: LocationsType)
}

final class LocationsController: UIView {
    let downloadManager = DownloadManager()
    var locations = [LocationsType]()
    weak var locationDelegate: LocationsDelegate?
    
    public lazy var locationTableView: UITableView = {
        let tableView = UITableView(frame: .zero, style: .plain)
        tableView.delegate = self
        tableView.dataSource = self
        tableView.tableFooterView = UIView()
        tableView.separatorInset.left = 16
        tableView.separatorInset.right = 16
        tableView.estimatedRowHeight = 200
        tableView.rowHeight = UITableView.automaticDimension
        tableView.sectionHeaderHeight = UITableView.automaticDimension
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: "default")
        tableView.showsVerticalScrollIndicator = false
        tableView.alwaysBounceVertical = false
        tableView.alpha = 0.0
        tableView.backgroundColor = Helper.Color.ViewController.whiteColor
        tableView.translatesAutoresizingMaskIntoConstraints = false
        return tableView
    }()

    override init(frame: CGRect) {
        super.init(frame: frame)
        setupView()
        downloadManager.getLocations { locations in
            guard let locations = locations else { return }
            self.locations.sort { $0.name < $1.name}
            Current.AllLocations = locations
        }
    }
    
    private func setupView() {
        addSubview(locationTableView)
        translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            locationTableView.topAnchor.constraint(equalTo: topAnchor),
            locationTableView.leadingAnchor.constraint(equalTo: leadingAnchor),
            locationTableView.trailingAnchor.constraint(equalTo: trailingAnchor),
            locationTableView.bottomAnchor.constraint(equalTo: bottomAnchor),
        ])
    }
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

extension LocationsController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        locations.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "default", for: indexPath)
        cell.textLabel?.text = locations[indexPath.row].name
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let location = locations[indexPath.row]
        locationDelegate?.getLocation(type: location)
        print(location)
        UIView.transition(with: locationTableView, duration: 0.4,
                          options: .transitionCrossDissolve,
                          animations: { [self] in
                            locationTableView.alpha = 0.0
                            tableView.deselectRow(at: indexPath, animated: true)
        })
    }
    
}
