//
//  CustomStepper.swift
//  BigBenApp
//
//  Created by Shalopay on 07.04.2023.
//

import UIKit
class CustomStepper: UIControl {
    var currentValue = 1 {
        didSet {
            currentValue = currentValue > 0 ? currentValue : 0
            currentStepValueLabel.text = "\(currentValue)"
        }
    }
    private lazy var decreaseButton: UIButton = {
           let button = UIButton()
            button.setTitleColor(.black, for: .normal)
            button.setTitle("-", for: .normal)
            button.addTarget(self, action: #selector(buttonAction), for: .touchUpInside)
            button.translatesAutoresizingMaskIntoConstraints = false
            return button
        }()
        
    private lazy var increaseButton: UIButton = {
        let button = UIButton()
        button.setTitle("+", for: .normal)
        button.addTarget(self, action: #selector(buttonAction), for: .touchUpInside)
        button.setTitleColor(.black, for: .normal)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()

    private lazy var currentStepValueLabel: UILabel = {
        var label = UILabel()
        label.textColor = .black
        label.text = "\(currentValue)"
        label.font = UIFont.monospacedDigitSystemFont(ofSize: 15, weight: UIFont.Weight.regular)
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    private lazy var mainHorizontalStackView: CustomStackView = {
        let stackView = CustomStackView(space: 15, axis: .horizontal, distribution: .equalSpacing, alignment: .center)
        return stackView
    }()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setupView()
    }
    
    private func setupView() {
        backgroundColor = .yellow
        layer.cornerRadius = 5
        addSubview(mainHorizontalStackView)
        mainHorizontalStackView.addArrangedSubview(decreaseButton)
        mainHorizontalStackView.addArrangedSubview(currentStepValueLabel)
        mainHorizontalStackView.addArrangedSubview(increaseButton)
        NSLayoutConstraint.activate([
            mainHorizontalStackView.topAnchor.constraint(equalTo: safeAreaLayoutGuide.topAnchor),
            mainHorizontalStackView.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor),
            mainHorizontalStackView.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor),
            mainHorizontalStackView.bottomAnchor.constraint(equalTo: safeAreaLayoutGuide.bottomAnchor),
        ])
    }
    
    @objc private func buttonAction(_ sender: UIButton) {
        switch sender {
            case decreaseButton:
                currentValue -= 1
            case increaseButton:
                currentValue += 1
            default:
                break
        }
        sendActions(for: .valueChanged)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
