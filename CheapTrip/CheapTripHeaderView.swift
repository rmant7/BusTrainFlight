//
//  CheapTripHeaderView.swift
//  CheapTrip
//
//  Created by Shalopay on 27.04.2023.
//

import UIKit

protocol HeaderViewDelegate: AnyObject {
    func expandedSection(sender: UIButton)
}

final class CheapTripHeaderView: UITableViewHeaderFooterView {
    static let identifier = "CheapTripHeaderView"
    let downloadManager = DownloadManager()
    var localDirectRoutes = [DirectRoutes]()
    weak var delegate: HeaderViewDelegate?
    
    private lazy var  mainView: UIView = {
       let view  = UIView()
        view.clipsToBounds = true
        view.layer.borderWidth = 1
        view.layer.borderColor = Helper.Color.NavigatorBar.redColor.cgColor
        view.layer.cornerRadius = 8
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }()
    
    private lazy var priceButton: CustomButton = {
        let button = CustomButton(title: "", font: Helper.Fonts.Regular(size: 15), backGroundColor: Helper.Color.NavigatorBar.redColor, textColor: Helper.Color.ViewController.whiteColor, cornerRadius: 8, lineHeightMultiple: 0, kern: 0)
        button.contentEdgeInsets = UIEdgeInsets(top: 0, left: 10, bottom: 4, right: 10)
        button.isUserInteractionEnabled = false
       return button
    }()
    
    private lazy var timeLabel: CustomLabel = {
        let label = CustomLabel(text: "", fontName: Helper.Fonts.Regular(size: 13), colorText: Helper.Color.ViewController.blackColor, lineHeightMultiple: 0, kern: 0)
        label.textAlignment = .left
        return label
    }()
    // airplane tram.fill bus.fill
    
    private lazy var iconsLabel: UILabel = {
       let label = UILabel()
        label.numberOfLines = 0
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    private lazy var namePointLabel: CustomLabel = {
        let label = CustomLabel(text: "", fontName: Helper.Fonts.Regular(size: 13), colorText: Helper.Color.ViewController.blackColor, lineHeightMultiple: 0, kern: 0)
        label.textAlignment = .left
        label.numberOfLines = 0
        return label
    }()
    
    public lazy var headerButton: UIButton = {
        let button = UIButton(type: .system)
        let image = UIImage(systemName: "control")?.withRenderingMode(.alwaysTemplate)
        button.imageView?.transform = CGAffineTransform(rotationAngle: CGFloat.pi)
        button.setImage(image, for: .normal)
        button.tintColor = Helper.Color.NavigatorBar.redColor
        button.addTarget(self, action: #selector(expandableCell(sender:)), for: .touchUpInside)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()
    
    override init(reuseIdentifier: String?) {
        super.init(reuseIdentifier: reuseIdentifier)
        setupView()
        downloadManager.getDirectRoutes { [self] directRoutes in
            guard let directRoutes = directRoutes else {return}
            localDirectRoutes = directRoutes
        }
    }
    
    
//    public func configureHeader(trip: Model, section: Int) {
//        namePointLabel.attributedText = NSAttributedString(string: trip.namePoint)
//        priceButton.setAttributedTitle(NSAttributedString(string: trip.price), for: .normal)
//        timeLabel.attributedText = NSAttributedString(string: trip.time)
//        iconsLabel.attributedText = setupIcons(names: trip.icons)
//        headerButton.tag = section
//    }
    
    private func getNameTrasport(type: Int) -> String {
        guard let transport = Current.CurrentTransportType.first(where: {$0.uuid == String(type)}), let icon = TypeTransfer(rawValue: transport.name) else {return "ERROR :\(#function)"}
        return icon.imageName
    }
    
    func getNamePoint(_ id: Int) -> String {
        guard let namePoint = Current.AllLocations.first(where:{ $0.uuid == String(id)
        }) else {return "ERROR: \(#function)"}
        return namePoint.name
    }
    
    
    
    /*
     "690210":{
                "from":69,
                "to":13,
                "transport":1,
                "price":23,
                "duration":65
     }
     */
    
    public func configureShalopay(trip: Routess, section: Int) {
        var iconsTransport = [String]()
        var namePoints = [String]()
        for route in trip.directRoutes {
            localDirectRoutes.forEach { directRoutes in
                if directRoutes.uuid == route {
                    iconsTransport.append(getNameTrasport(type: directRoutes.transport))
                    //print(getNamePoint(directRoutes.from), (getNamePoint(directRoutes.to)))
                    namePoints.append(getNamePoint(directRoutes.from))
                    namePoints.append(getNamePoint(directRoutes.to))
                }
            }
        }
        namePointLabel.attributedText = setupName(namePoints)
        priceButton.setAttributedTitle(NSAttributedString(string: "€ \(trip.price)"), for: .normal)
        timeLabel.attributedText = NSAttributedString(string: "\(trip.duration.getDay())")
        iconsLabel.attributedText = setupIcons(names: iconsTransport)
        headerButton.tag = section
    }
    
    private func setupIcons(names: [String]) -> NSMutableAttributedString {
        let completeText = NSMutableAttributedString(string: "")
        names.forEach { name in
            let imageAttachment = NSTextAttachment()
            imageAttachment.image = UIImage(systemName: name)
            let attachmentString = NSAttributedString(attachment: imageAttachment)
            let emptyattachmentString = NSAttributedString(string: " ")
            completeText.append(attachmentString)
            completeText.append(emptyattachmentString)
        }
       return completeText
    }
    
    private func setupName(_ points: [String]) -> NSMutableAttributedString {
        let completeText = NSMutableAttributedString(string: "")
        points.forEach { point in
            let attachmentString = NSAttributedString(string: point)
            let emptyattachmentString = NSAttributedString(string: " > ")
            completeText.append(attachmentString)
            completeText.append(emptyattachmentString)
        }
        completeText.deleteCharacters(in: NSRange(location: (completeText.length) - 3, length: 3))
       return completeText
    }
    
    private func setupView() {
        contentView.backgroundColor = Helper.Color.ViewController.whiteColor
        contentView.addSubview(mainView)
        
        mainView.addSubviews([
            headerButton, priceButton, timeLabel, iconsLabel, namePointLabel
        ])
        
        NSLayoutConstraint.activate([
            mainView.topAnchor.constraint(equalTo: contentView.safeAreaLayoutGuide.topAnchor, constant: 10),
            mainView.leadingAnchor.constraint(equalTo: contentView.safeAreaLayoutGuide.leadingAnchor, constant: 19),
            mainView.trailingAnchor.constraint(equalTo: contentView.safeAreaLayoutGuide.trailingAnchor, constant: -19),
            mainView.bottomAnchor.constraint(equalTo: contentView.safeAreaLayoutGuide.bottomAnchor, constant: -10),
            
            
            priceButton.leadingAnchor.constraint(equalTo: mainView.leadingAnchor,constant: -4),
            priceButton.bottomAnchor.constraint(equalTo: mainView.bottomAnchor, constant: 4),
            //priceButton.heightAnchor.constraint(equalToConstant: 27),
            
            timeLabel.centerYAnchor.constraint(equalTo: priceButton.centerYAnchor),
            timeLabel.leadingAnchor.constraint(equalTo: priceButton.trailingAnchor, constant: 10),
            
            iconsLabel.topAnchor.constraint(equalTo: mainView.topAnchor, constant: 13),
            iconsLabel.leadingAnchor.constraint(equalTo: mainView.leadingAnchor, constant: 10),
            iconsLabel.trailingAnchor.constraint(equalTo: mainView.trailingAnchor, constant: -10),
            
            headerButton.trailingAnchor.constraint(equalTo: mainView.trailingAnchor, constant: -10),
            headerButton.centerYAnchor.constraint(equalTo: mainView.centerYAnchor),
            headerButton.heightAnchor.constraint(equalToConstant: 15),
            headerButton.widthAnchor.constraint(equalToConstant: 15),
            
            namePointLabel.topAnchor.constraint(equalTo: iconsLabel.bottomAnchor, constant: 10),
            namePointLabel.leadingAnchor.constraint(equalTo: mainView.leadingAnchor, constant: 10),
            namePointLabel.trailingAnchor.constraint(equalTo: headerButton.leadingAnchor, constant: -5),
            namePointLabel.bottomAnchor.constraint(equalTo: priceButton.topAnchor, constant: -15),
        ])
        
    }
    
    public func rotateImage(_ expanded: Bool) {
            if expanded {
                headerButton.imageView?.transform = CGAffineTransform(rotationAngle: CGFloat.pi)
            } else {
                headerButton.imageView?.transform = CGAffineTransform(rotationAngle: CGFloat.zero)
            }
    }
    
    @objc private func expandableCell(sender: UIButton) {
        delegate?.expandedSection(sender: sender)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
