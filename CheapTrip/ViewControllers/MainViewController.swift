//
//  MainViewController.swift
//  CheapTrip
//
//  Created by Shalopay on 27.04.2023.
//

import UIKit

enum typeTextFiled {
    case fromTextField
    case toTextField
}

final class MainViewController: UIViewController {
    
    var typeTextFiled: typeTextFiled = .fromTextField
    let downloadManager = DownloadManager()
    var locationsController = LocationsController()
    var startFromPoint:LocationsType?
    var endToPoint:LocationsType?
    
    //var arrayTrip: [Model] = [Model]()
    var shalopay: [Routess] = [Routess]()
    
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
        textField.tag = 0
        textField.layer.borderColor = Helper.Color.NavigatorBar.redColor.cgColor
        textField.addTarget(self, action: #selector(tapingFrom(sender:)), for: .editingChanged)
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
        textField.tag = 1
        textField.layer.borderColor = Helper.Color.NavigatorBar.redColor.cgColor
        textField.addTarget(self, action: #selector(tapingFrom(sender:)), for: .editingChanged)
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
        //arrayTrip = Helper.trips
        
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        locationsController.locationDelegate = self
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
            locationsController, fromTextField, toTextField, titleToLabel, titleFromLabel, mainHorizontalStackView, logoImageView, cheapTripTableView
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
            cheapTripTableView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: 0),
            
            //locationsController.topAnchor.constraint(equalTo: fromTextField.bottomAnchor),
            locationsController.leadingAnchor.constraint(equalTo: fromTextField.leadingAnchor),
            locationsController.trailingAnchor.constraint(equalTo: fromTextField.trailingAnchor),
            locationsController.bottomAnchor.constraint(equalTo: view.bottomAnchor),
        ])
    }
    
    @objc private func tapingFrom(sender: CustomTextField) {
        guard let searchText = sender.text else {return}
        print(sender.tag)
        if sender.tag == 0 {
            typeTextFiled = .fromTextField
            //locationsController.topAnchor.constraint(equalTo: fromTextField.bottomAnchor).isActive = true
        } else {
            typeTextFiled = .toTextField
            locationsController.topAnchor.constraint(equalTo: toTextField.bottomAnchor).isActive = true
        }
        
        locationsController.locations = Current.AllLocations.filter{$0.name.contains(searchText)}
        locationsController.locationTableView.reloadData()
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
        print(startFromPoint?.name, startFromPoint?.uuid)
        print(endToPoint?.name, endToPoint?.uuid)
        guard let startFromPoint = startFromPoint, let endToPoint = endToPoint else {return print("OPS")}
        if sender.tag == 0 {
            print("tap clear from")
            cheapTripTableView.isHidden = true
            logoImageView.isHidden = false
            
        } else {
            print("tap lets go")
            cheapTripTableView.isHidden = false
            logoImageView.isHidden = true
            downloadManager.getRoutes { [self] result in
                guard let result = result else {return}
                    //let allfromRoutes = result.filter({ $0.from == Int(startFromPoint.uuid) && $0.to == Int(endToPoint.uuid) })
                    let unicalTrip = self.removeDuplicates(array: result.filter({ $0.from == Int(startFromPoint.uuid) && $0.to == Int(endToPoint.uuid) }))
                    print(unicalTrip)
                    shalopay = unicalTrip
                    DispatchQueue.main.async {
                        cheapTripTableView.reloadData()
                    }
                }
        }
    }
    
    private func removeDuplicates(array: [Routess]) -> [Routess] {
        var result = [Routess]()
        for value in array {
            if !result.contains(where: { $0.directRoutes == value.directRoutes }) {
                result.append(value)
            }
        }
        return result
    }
}

extension MainViewController: UITableViewDelegate, UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        //arrayTrip.count
        return shalopay.count
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
//        if arrayTrip[section].isExpanded {
//            return 0
//        }
//        return arrayTrip[section].transfer.count
        0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: CheapTripDetailCell.identifier, for: indexPath) as? CheapTripDetailCell else { return UITableViewCell(frame: .zero)}
        //let transfer = arrayTrip[indexPath.section].transfer[indexPath.row]
        //cell.configureCell(transfer: transfer)
        return cell
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        guard let header = tableView.dequeueReusableHeaderFooterView(withIdentifier: CheapTripHeaderView.identifier) as? CheapTripHeaderView else {return UIView()}
        header.delegate = self
        //let trip = arrayTrip[section]
        let trip = shalopay[section]
        //header.configureHeader(trip: trip, section: section)
//        header.rotateImage(trip.isExpanded)
        header.configureShalopay(trip: trip, section: section)
        
        return header
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("SECTION: \(indexPath.section) - ROW: \(indexPath.row)")
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

extension MainViewController: HeaderViewDelegate {
    func expandedSection(sender: UIButton) {
//        let section = sender.tag
//        let isExpanded = arrayTrip[section].isExpanded
//        arrayTrip[section].isExpanded = !isExpanded
//        cheapTripTableView.reloadSections(IndexSet(integer: section), with: .automatic)
    }
}

extension MainViewController: UITextFieldDelegate {
    
    private func animationTo(_ localView: LocationsController, alpha: CGFloat) {
        UIView.transition(with: localView.locationTableView, duration: 0.4,
                          options: .transitionCrossDissolve,
                          animations: {
                            self.view.sendSubviewToBack(localView)
                        localView.locationTableView.alpha = alpha
        })
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        animationTo(locationsController, alpha: 0.0)
        return view.endEditing(true)
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
            let empty = textField.text?.isEmpty ?? true
            if(empty) {
                textField.text = string.uppercased()
                return false
            }
            animationTo(locationsController, alpha: 1.0)
            self.view.bringSubviewToFront(self.locationsController)
            return true
    }
}

extension MainViewController: LocationsDelegate {
    func getLocation(type: LocationsType) {
        if typeTextFiled == .fromTextField {
            fromTextField.text = type.name
            startFromPoint = type
        } else {
            toTextField.text = type.name
            endToPoint = type
        }
    
    }
}
