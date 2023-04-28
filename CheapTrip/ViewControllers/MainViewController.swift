//
//  MainViewController.swift
//  CheapTrip
//
//  Created by Shalopay on 27.04.2023.
//

import UIKit

final class MainViewController: UIViewController {
    let downloadManager = DownloadManager()
    
    var arrayTrip: [Model] = [Model]()
    
    private lazy var logoImageView: UIImageView = {
        let imageView = UIImageView(image: UIImage(named: "logo"))
        imageView.clipsToBounds = true
        imageView.contentMode = .scaleAspectFill
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    
    private lazy var leftLabel: CustomLabel = {
        let label = CustomLabel(text: "CheapTrip", fontName: Helper.Fonts.Regular(size: 29), colorText: Helper.Color.ViewController.whiteColor, lineHeightMultiple: 0, kern: 0)
        label.textAlignment = .left
        return label
    }()
    
    private lazy var rightLabel: CustomLabel = {
        let label = CustomLabel(text: "Pay less, visit more!", fontName: Helper.Fonts.SemiBold(size: 16), colorText: Helper.Color.ViewController.whiteColor, lineHeightMultiple: 0, kern: 0)
        label.textAlignment = .left
        return label
    }()
    
    private lazy var closeFromButton: UIButton = {
        let button = UIButton(type: .system)
        button.setImage(UIImage(systemName: "multiply.circle"), for: .normal)
        button.tintColor = Helper.Color.NavigatorBar.redColor
        button.addTarget(self, action: #selector(clearTextFiled(sender:)), for: .touchUpInside)
        button.contentEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 10)
        button.tag = 0
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()
    private lazy var closeToButton: UIButton = {
        let button = UIButton(type: .system)
        button.setImage(UIImage(systemName: "multiply.circle"), for: .normal)
        button.tintColor = Helper.Color.NavigatorBar.redColor
        button.addTarget(self, action: #selector(clearTextFiled(sender:)), for: .touchUpInside)
        button.contentEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 10)
        button.tag = 1
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()
    
    private lazy var titleFromLabel: CustomLabel = {
        let label = CustomLabel(text: "From", fontName: Helper.Fonts.Regular(size: 15), colorText: Helper.Color.ViewController.blackColor, lineHeightMultiple: 0, kern: 0)
        label.textAlignment = .left
        return label
    }()
    private lazy var fromTextField: CustomTextField = {
        let textField = CustomTextField(holder: "from", colorText: Helper.Color.ViewController.blackColor, fontName: Helper.Fonts.Medium(size: 15), cornerRadius: 8)
        textField.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 16, height: textField.frame.height))
        textField.leftViewMode = .always
        textField.rightView = closeFromButton
        textField.rightViewMode = .always
        textField.textAlignment = .left
        textField.layer.borderWidth = 1
        textField.delegate = self
        textField.keyboardType = .namePhonePad
        textField.autocapitalizationType = .words
        textField.returnKeyType = .send
        textField.layer.borderColor = Helper.Color.NavigatorBar.redColor.cgColor
        return textField
    }()
    
    private lazy var titleToLabel: CustomLabel = {
        let label = CustomLabel(text: "To", fontName: Helper.Fonts.Regular(size: 15), colorText: Helper.Color.ViewController.blackColor, lineHeightMultiple: 0, kern: 0)
        label.textAlignment = .left
        return label
    }()
    private lazy var toTextField: CustomTextField = {
        let textField = CustomTextField(holder: "to", colorText: Helper.Color.ViewController.blackColor, fontName: Helper.Fonts.Medium(size: 15), cornerRadius: 8)
        textField.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 16, height: textField.frame.height))
        textField.leftViewMode = .always
        textField.rightView = closeToButton
        textField.rightViewMode = .always
        textField.textAlignment = .left
        textField.layer.borderWidth = 1
        textField.delegate = self
        textField.keyboardType = .namePhonePad
        textField.autocapitalizationType = .words
        textField.returnKeyType = .send
        textField.layer.borderColor = Helper.Color.NavigatorBar.redColor.cgColor
        return textField
    }()
    
    private lazy var clearButton: CustomButton = {
        let button = CustomButton(title: "Clear form", font: Helper.Fonts.SemiBold(size: 15), backGroundColor: Helper.Color.ViewController.whiteColor, textColor: Helper.Color.ViewController.blackColor, cornerRadius: 8, lineHeightMultiple: 0, kern: 0)
        button.contentEdgeInsets = UIEdgeInsets(top: 9, left: 24, bottom: 9, right: 24)
        button.layer.borderWidth = 1
        button.layer.borderColor = Helper.Color.NavigatorBar.redColor.cgColor
        button.tag = 0
        button.addTarget(self, action: #selector(tapingButton(sender:)), for: .touchUpInside)
       return button
    }()
    private lazy var letsGoButton: CustomButton = {
        let button = CustomButton(title: "Let's go", font: Helper.Fonts.SemiBold(size: 15), backGroundColor: Helper.Color.NavigatorBar.redColor, textColor: Helper.Color.ViewController.whiteColor, cornerRadius: 8, lineHeightMultiple: 0, kern: 0)
        button.contentEdgeInsets = UIEdgeInsets(top: 9, left: 24, bottom: 9, right: 24)
        button.tag = 1
        button.addTarget(self, action: #selector(tapingButton(sender:)), for: .touchUpInside)
       return button
    }()
    private lazy var mainHorizontalStackView: CustomStackView = {
        let stackView = CustomStackView(space: 18, axis: .horizontal, distribution: .fillEqually, alignment: .center)
        return stackView
    }()
    
    public lazy var cheapTripTableView: UITableView = {
        let tableView = UITableView(frame: view.bounds, style: .plain)
        tableView.delegate = self
        tableView.dataSource = self
        tableView.tableFooterView = UIView()
        tableView.estimatedRowHeight = 200
        tableView.rowHeight = UITableView.automaticDimension
        tableView.estimatedSectionHeaderHeight = 150
        tableView.sectionHeaderHeight = UITableView.automaticDimension
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: "default")
        tableView.register(CheapTripDetailCell.self, forCellReuseIdentifier: CheapTripDetailCell.identifier)
        tableView.register(CheapTripHeaderView.self, forHeaderFooterViewReuseIdentifier: CheapTripHeaderView.identifier)
        tableView.showsVerticalScrollIndicator = false
        tableView.alwaysBounceVertical = false
        tableView.backgroundColor = .clear
        tableView.isHidden = true
        tableView.translatesAutoresizingMaskIntoConstraints = false
        return tableView
    }()
    
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        arrayTrip = Helper.trips
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
        
    private func setupView() {
        view.backgroundColor = Helper.Color.ViewController.whiteColor
        navigationController?.navigationBar.barTintColor = Helper.Color.NavigatorBar.redColor
        navigationItem.leftBarButtonItem = UIBarButtonItem(customView: leftLabel)
        
        let textItem = UIBarButtonItem(customView: rightLabel)
        let buttonItem = UIBarButtonItem(image: UIImage(systemName: "line.horizontal.3"), style: .done, target: self, action: #selector(tapingSetting))
            buttonItem.tintColor = Helper.Color.ViewController.whiteColor
        navigationItem.rightBarButtonItems = [buttonItem, textItem]
        
        view.addSubviews([
            fromTextField, toTextField, titleToLabel, titleFromLabel, mainHorizontalStackView, logoImageView, cheapTripTableView
        ])
        
        mainHorizontalStackView.addArrangedSubview(clearButton)
        mainHorizontalStackView.addArrangedSubview(letsGoButton)
        
        NSLayoutConstraint.activate([
            
            titleFromLabel.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 15),
            titleFromLabel.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 19),
            titleFromLabel.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: -19),
            
            fromTextField.topAnchor.constraint(equalTo: titleFromLabel.bottomAnchor, constant: 3),
            fromTextField.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 19),
            fromTextField.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: -19),
            fromTextField.heightAnchor.constraint(equalToConstant: 41),
            
            titleToLabel.topAnchor.constraint(equalTo: fromTextField.bottomAnchor, constant: 13),
            titleToLabel.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 19),
            titleToLabel.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: -19),
            
            
            toTextField.topAnchor.constraint(equalTo: titleToLabel.bottomAnchor, constant: 3),
            toTextField.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 19),
            toTextField.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: -19),
            toTextField.heightAnchor.constraint(equalToConstant: 41),
            
            mainHorizontalStackView.topAnchor.constraint(equalTo: toTextField.bottomAnchor, constant: 14),
            mainHorizontalStackView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 19),
            mainHorizontalStackView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: -19),
            
            logoImageView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            logoImageView.topAnchor.constraint(equalTo: mainHorizontalStackView.bottomAnchor, constant: 20),
            logoImageView.heightAnchor.constraint(equalToConstant: view.frame.width),
            logoImageView.widthAnchor.constraint(equalToConstant: view.frame.width),
            
            cheapTripTableView.topAnchor.constraint(equalTo: mainHorizontalStackView.bottomAnchor, constant: 20),
            cheapTripTableView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 0),
            cheapTripTableView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: 0),
            cheapTripTableView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: 0)
            
        ])
    }
    
    @objc private func tapingSetting() {
        print(#function)
    }
    
    @objc private func clearTextFiled(sender: UIButton) {
        print(sender.tag)
        if sender.tag == 0 {
            fromTextField.text = ""
        } else {
            toTextField.text = ""
        }
    }
    
    @objc private func tapingButton(sender: CustomButton) {
        sender.animationPressButton()
        if sender.tag == 0 {
            print("tap clear from")
            cheapTripTableView.isHidden = true
            logoImageView.isHidden = false
        } else {
            print("tap lets go")
            cheapTripTableView.isHidden = false
            logoImageView.isHidden = true
        }
    }
}

extension MainViewController: UITableViewDelegate, UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        arrayTrip.count
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if arrayTrip[section].isExpanded {
            return 0
        }
        return arrayTrip[section].transfer.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: CheapTripDetailCell.identifier, for: indexPath) as? CheapTripDetailCell else { return UITableViewCell(frame: .zero)}
        let transfer = arrayTrip[indexPath.section].transfer[indexPath.row]
        cell.configureCell(transfer: transfer)
        return cell
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        guard let header = tableView.dequeueReusableHeaderFooterView(withIdentifier: CheapTripHeaderView.identifier) as? CheapTripHeaderView else {return UIView()}
        header.delegate = self
        let trip = arrayTrip[section]
        header.configureHeader(trip: trip, section: section)
        header.rotateImage(trip.isExpanded)
        return header
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("SECTION: \(indexPath.section) - ROW: \(indexPath.row)")
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

extension MainViewController: HeaderViewDelegate {
    func expandedSection(sender: UIButton) {
        let section = sender.tag
        let isExpanded = arrayTrip[section].isExpanded
        arrayTrip[section].isExpanded = !isExpanded
        cheapTripTableView.reloadSections(IndexSet(integer: section), with: .automatic)
    }
}

extension MainViewController: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        view.endEditing(true)
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
            let empty = textField.text?.isEmpty ?? true
            if(empty) {
                textField.text = string.uppercased()
                return false
            }

            return true
        }
}
