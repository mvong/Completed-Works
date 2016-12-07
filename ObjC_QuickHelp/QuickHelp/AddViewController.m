//
//  AddViewController.m
//  QuickHelp
//
//  Created by Mark on 12/5/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import "AddViewController.h"
@import Firebase;

@interface AddViewController () <UITextFieldDelegate, UITextViewDelegate>
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (nonatomic) NSUInteger selectedSegment;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *saveButton;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *backButton;
@property (strong, nonatomic) FIRDatabaseReference *fdbRef;
@end

@implementation AddViewController

@synthesize addSegmentControl;
@synthesize addDescriptionTextView;
@synthesize addressTextField;
@synthesize cityTextField;
@synthesize zipcodeTextField;

UITextField* activeField;

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self registerForKeyboardNotifications];
    self.fdbRef =[[FIRDatabase database] reference];
    self.addDescriptionTextView.delegate = self;
    self.addressTextField.delegate = self;
    self.cityTextField.delegate = self;
    self.zipcodeTextField.delegate = self;
    self.saveButton.enabled = false;
    self.fdbRef = [self.fdbRef child:@"Service"];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)registerForKeyboardNotifications
{
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWasShown:)
                                                 name:UIKeyboardDidShowNotification object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillBeHidden:)
                                                 name:UIKeyboardWillHideNotification object:nil];
    
}
- (void)keyboardWasShown:(NSNotification*)aNotification
{
    NSDictionary* info = [aNotification userInfo];
    CGSize kbSize = [[info objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size;
    
    UIEdgeInsets contentInsets = UIEdgeInsetsMake(0.0, 0.0, kbSize.height+15, 0.0);
    self.scrollView.contentInset = contentInsets;
    self.scrollView.scrollIndicatorInsets = contentInsets;
    
    // If active text field is hidden by keyboard, scroll it so it's visible
    // Your app might not need or want this behavior.
    CGRect aRect = self.view.frame;
    aRect.size.height -= kbSize.height;
    if (!CGRectContainsPoint(aRect, activeField.frame.origin) ) {
        [self.scrollView scrollRectToVisible:activeField.frame animated:YES];
    }
}

- (void)keyboardWillBeHidden:(NSNotification*)aNotification
{
    UIEdgeInsets contentInsets = UIEdgeInsetsZero;
    self.scrollView.contentInset = contentInsets;
    self.scrollView.scrollIndicatorInsets = contentInsets;
}

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    activeField = textField;
}

- (void)textFieldDidEndEditing:(UITextField *)textField
{
    activeField = nil;
}

- (IBAction)pickSegmentedControl:(id)sender {
    self.selectedSegment = [addSegmentControl selectedSegmentIndex];
}

- (void) enableSaveButtonForService:(NSString*) service
                            address:(NSString*) address
                               city:(NSString*) city
                            zipcode:(NSString*) zipcode {
    self.saveButton.enabled = (service.length > 0 && address.length > 0 && city.length > 0 && zipcode.length > 0);
}

- (BOOL) textField: (UITextField* ) textField shouldChangeCharactersInRange: (NSRange) range
 replacementString: (NSString* ) string {
    
    [self addSelectors];
    
    return YES;
}

-(BOOL) textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text {
    
    NSString* changedString = [textView.text stringByReplacingCharactersInRange:range withString:text];
    
    [self enableSaveButtonForService: changedString
                             address: self.addressTextField.text
                                city: self.cityTextField.text
                             zipcode: self.zipcodeTextField.text];
    
    return YES;
}

-(BOOL) textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    textField.text = nil;
    return YES;
}

-(void) touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
    UITouch* touch = [touches anyObject];
    if(touch.phase == UITouchPhaseBegan) {
        [self.addDescriptionTextView resignFirstResponder];
        [self.addressTextField resignFirstResponder];
        [self.cityTextField resignFirstResponder];
        [self.zipcodeTextField resignFirstResponder];
    }
}

-(void)textFieldChanged:(UITextField*)textField {
    if(self.addDescriptionTextView.text.length > 0
       && self.addressTextField.text.length > 0
       && self.cityTextField.text.length > 0
       && self.zipcodeTextField.text.length > 0) {
        self.saveButton.enabled = YES;
    } else {
        self.saveButton.enabled = NO;
    }
}

-(void) addSelectors {
    [self.addressTextField addTarget:self action:@selector(textFieldChanged:) forControlEvents:UIControlEventEditingChanged];
    [self.cityTextField addTarget:self action:@selector(textFieldChanged:) forControlEvents:UIControlEventEditingChanged];
    [self.zipcodeTextField addTarget:self action:@selector(textFieldChanged:) forControlEvents:UIControlEventEditingChanged];
}


- (IBAction)addBackButtonClicked:(id)sender {
    if(self.AddService) {
        self.AddService(0,
                        nil,
                        nil,
                        nil,
                        nil);
    }
    self.addSegmentControl.selectedSegmentIndex = 0;
    self.addDescriptionTextView.text = nil;
    self.addressTextField.text = nil;
    self.cityTextField.text = nil;
    self.zipcodeTextField.text = nil;
    [self performSegueWithIdentifier:@"return_feed" sender:self];
}
- (IBAction)saveButtonClicked:(id)sender {
    NSDictionary* dictionary;
    if(self.AddService) {
        self.AddService(self.addSegmentControl.selectedSegmentIndex,
                        self.addDescriptionTextView.text,
                        self.addressTextField.text,
                        self.cityTextField.text,
                        self.zipcodeTextField.text);
    }
    
    dictionary = [[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInteger:self.addSegmentControl.selectedSegmentIndex], @"Category",
                  self.addDescriptionTextView.text, @"Description",
                  self.addressTextField.text,@"Address",
                  self.cityTextField.text, @"City",
                  self.zipcodeTextField.text, @"Zipcode", nil];
    FIRDatabaseReference* ref = [self.fdbRef childByAutoId];
    [ref setValue:dictionary];
    NSString* currUser = [[FIRAuth auth]currentUser].email;
    NSLog(@"%@ added something!", currUser);
    self.addSegmentControl.selectedSegmentIndex = 0;
    self.addDescriptionTextView.text = nil;
    self.addressTextField.text = nil;
    self.cityTextField.text = nil;
    self.zipcodeTextField.text = nil;
    [self performSegueWithIdentifier:@"return_feed" sender:self];
}




/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
