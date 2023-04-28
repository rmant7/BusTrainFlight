//
//  CheapTripDetailCell.swift
//  CheapTrip
//
//  Created by Shalopay on 27.04.2023.
//

import UIKit

class CheapTripDetailCell: UITableViewCell {
    static let identifier = "CheapTripDetailCell"
    
    private lazy var namePointLabel: CustomLabel = {
        let label = CustomLabel(text: "Kyoto > Aizawl", fontName: Helper.Fonts.SemiBold(size: 13), colorText: Helper.Color.ViewController.blackColor, lineHeightMultiple: 0, kern: 0)
        label.textAlignment = .left
        label.numberOfLines = 0
        return label
    }()
    
    private lazy var typeTransportLabel: CustomLabel = {
        let label = CustomLabel(text: "Flight", fontName: Helper.Fonts.SemiBold(size: 13), colorText: Helper.Color.ViewController.blackColor, lineHeightMultiple: 0, kern: 0)
        label.textAlignment = .right
        label.numberOfLines = 0
        return label
    }()
    
    private lazy var timeLabel: CustomLabel = {
        let label = CustomLabel(text: "22h 10min", fontName: Helper.Fonts.Regular(size: 13), colorText: Helper.Color.ViewController.blackColor, lineHeightMultiple: 0, kern: 0)
        label.textAlignment = .left
        return label
    }()
    
    private lazy var priceButton: CustomButton = {
        let button = CustomButton(title: "122.00", font: Helper.Fonts.Regular(size: 13), backGroundColor: Helper.Color.ViewController.whiteColor, textColor: Helper.Color.ViewController.blackColor, cornerRadius: 0, lineHeightMultiple: 0, kern: 0)
        button.contentEdgeInsets = UIEdgeInsets(top: 0, left: 10, bottom: 4, right: 0)
        button.isUserInteractionEnabled = false
       return button
    }()
    
    private lazy var BookingButton: CustomButton = {
        let button = CustomButton(title: "Booking.com", font: Helper.Fonts.SemiBold(size: 13), backGroundColor: Helper.Color.NavigatorBar.redColor, textColor: Helper.Color.ViewController.whiteColor, cornerRadius: 8, lineHeightMultiple: 0, kern: 0)
        button.contentEdgeInsets = UIEdgeInsets(top: 6, left: 6, bottom: 6, right: 6)
        button.tag = 0
        button.addTarget(self, action: #selector(tapingButton(sender:)), for: .touchUpInside)
       return button
    }()
    private lazy var BuyTicketButton: CustomButton = {
        let button = CustomButton(title: "Buy ticket", font: Helper.Fonts.SemiBold(size: 13), backGroundColor: Helper.Color.NavigatorBar.redColor, textColor: Helper.Color.ViewController.whiteColor, cornerRadius: 8, lineHeightMultiple: 0, kern: 0)
        button.contentEdgeInsets = UIEdgeInsets(top: 6, left: 6, bottom: 6, right: 6)
        button.tag = 1
        button.addTarget(self, action: #selector(tapingButton(sender:)), for: .touchUpInside)
       return button
    }()
    private lazy var hostelWorldButton: CustomButton = {
        let button = CustomButton(title: "Hostelworld", font: Helper.Fonts.SemiBold(size: 13), backGroundColor: Helper.Color.NavigatorBar.redColor, textColor: Helper.Color.ViewController.whiteColor, cornerRadius: 8, lineHeightMultiple: 0, kern: 0)
        button.contentEdgeInsets = UIEdgeInsets(top: 6, left: 6, bottom: 6, right: 6)
        button.tag = 2
        button.addTarget(self, action: #selector(tapingButton(sender:)), for: .touchUpInside)
       return button
    }()
    private lazy var mainHorizontalStackView: CustomStackView = {
        let stackView = CustomStackView(space: 18, axis: .horizontal, distribution: .fillEqually, alignment: .center)
        return stackView
    }()

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        setupView()
    }
    
    public func configureCell(transfer: Transfer) {
        namePointLabel.attributedText = NSAttributedString(string: transfer.name)
        timeLabel.attributedText = NSAttributedString(string: transfer.time)
        priceButton.setAttributedTitle(NSAttributedString(string: transfer.price), for: .normal)
        typeTransportLabel.attributedText = NSAttributedString(string: transfer.type.rawValue)
    }
    
    private func setupView() {
        contentView.addSubviews([
            namePointLabel, typeTransportLabel, timeLabel, priceButton, mainHorizontalStackView
        ])
        
        mainHorizontalStackView.addArrangedSubview(BookingButton)
        mainHorizontalStackView.addArrangedSubview(BuyTicketButton)
        mainHorizontalStackView.addArrangedSubview(hostelWorldButton)
        
        NSLayoutConstraint.activate([
            namePointLabel.topAnchor.constraint(equalTo: contentView.topAnchor, constant: 7),
            namePointLabel.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 38),
            
            typeTransportLabel.centerYAnchor.constraint(equalTo: namePointLabel.centerYAnchor),
            typeTransportLabel.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -38),
            
            timeLabel.topAnchor.constraint(equalTo: namePointLabel.bottomAnchor, constant: 5),
            timeLabel.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 38),
            
            priceButton.centerYAnchor.constraint(equalTo: timeLabel.centerYAnchor),
            priceButton.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -38),
            
            mainHorizontalStackView.topAnchor.constraint(equalTo: timeLabel.bottomAnchor, constant: 22),
            mainHorizontalStackView.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 38),
            mainHorizontalStackView.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -38),
            mainHorizontalStackView.bottomAnchor.constraint(equalTo: contentView.bottomAnchor, constant: -10),
            
        ])
    }
    
    @objc private func tapingButton(sender: CustomButton) {
        sender.animationPressButton()
        switch sender.tag {
        case 0:
            print("Taping Booking")
        case 1:
            print("Taping Buy ticket")
        case 2:
            print("Taping HostelWorld")
        default:
            break
        }
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
