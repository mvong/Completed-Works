//
//  ViewController.m
//  QuickHelp
//
//  Created by Mark on 12/5/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import "ViewController.h"
@import Firebase;
@interface ViewController () <UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextField *usernameTextField;
@property (weak, nonatomic) IBOutlet UITextField *passwordTextField;
@property (weak, nonatomic) IBOutlet UIButton *createButton;
@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

// Login button clicked (check for user authenticity)
- (IBAction)loginClicked:(id)sender {
    [[FIRAuth auth] signInWithEmail:self.usernameTextField.text
                           password:self.passwordTextField.text
                         completion:^(FIRUser * _Nullable user,
                                      NSError * _Nullable error) {
                             if(error) {
                                 [self showAlertWithError:error];
                             } else {
                                 [self performSegueWithIdentifier:@"login_success" sender:self];
                             }
    
                         }];
}
// Create account button clicked
- (IBAction)createClicked:(id)sender {
    [[FIRAuth auth] createUserWithEmail:self.usernameTextField.text
                               password:self.passwordTextField.text
                             completion: ^(FIRUser *_Nullable user,
                                           NSError *_Nullable error){
                                 if(error) {
                                     [self showAlertWithError:error];
                                     
                                 } else {
                                     [self performSegueWithIdentifier:@"login_success" sender:self];
                                 }
                             }];
    
}

// From lecture slides
-(void) showAlertWithError:(NSError*) error {
    UIAlertController *alertController = [UIAlertController
                                          alertControllerWithTitle:@"Error!"
                                          message:[error localizedDescription]
                                          preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *cancelAction = [UIAlertAction
                                   actionWithTitle:@"Cancel"
                                   style:UIAlertActionStyleCancel
                                   handler:^(UIAlertAction *action)
    {
        NSLog(@"Cancel clicked");
    }];
    UIAlertAction *okAction = [UIAlertAction
                               actionWithTitle:@"OK"
                               style:UIAlertActionStyleDefault
                               handler:^(UIAlertAction *action)
    {
        NSLog(@"OK clicked");
    }];
    [alertController addAction:cancelAction];
    [alertController addAction:okAction];
    [self presentViewController:alertController animated:YES completion:nil];
}

// Hide keyboard if user hits return key
-(BOOL) textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    return YES;
}
// Hide keyboard based on user touch
-(void) touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
    UITouch* touch = [touches anyObject];
    if(touch.phase == UITouchPhaseBegan) {
        [self.usernameTextField resignFirstResponder];
        [self.passwordTextField resignFirstResponder];
    }
}

@end
