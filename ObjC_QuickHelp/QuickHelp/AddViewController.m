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
@property (weak, nonatomic) IBOutlet UIBarButtonItem *saveButton;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *backButton;
@property (strong, nonatomic) FIRDatabaseReference *fdbRef;
@property (strong, nonatomic) PostsModel* addPostsModel;
@end

@implementation AddViewController

@synthesize addSegmentControl;
@synthesize addDescriptionTextView;
@synthesize addressTextField;
@synthesize cityTextField;
@synthesize zipcodeTextField;

UITextField* activeField;
UInt8 refHandle;
- (void)viewDidLoad {
    [super viewDidLoad];
    self.addPostsModel = [PostsModel sharedPostsModel]; //Get singleton
    [self registerForKeyboardNotifications];
    
    self.fdbRef =[[FIRDatabase database] reference]; // Get reference to firebase database
    self.addDescriptionTextView.delegate = self; // Set fields' delegates to self
    self.addressTextField.delegate = self;       //
    self.cityTextField.delegate = self;          //
    self.zipcodeTextField.delegate = self;       //
    self.saveButton.enabled = false; // Set save button initially to false
    self.fdbRef = [self.fdbRef child:@"Service"]; // Set database node to 'Service'
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void)registerForKeyboardNotifications {
    [[NSNotificationCenter defaultCenter] addObserver:self // Tells when keyboard is to show up
                                             selector:@selector(keyboardWasShown:)
                                                 name:UIKeyboardDidShowNotification object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self // Tells when keyboard is to hide
                                             selector:@selector(keyboardWillBeHidden:)
                                                 name:UIKeyboardWillHideNotification object:nil];
    
}
// From stackoverflow - used to scoot view up so keyboard doesn't block textfields
- (void)keyboardWasShown:(NSNotification*)aNotification {
    NSDictionary* info = [aNotification userInfo];
    CGSize kbSize = [[info objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size;
    
    UIEdgeInsets contentInsets = UIEdgeInsetsMake(0.0, 0.0, kbSize.height+15, 0.0);
    self.scrollView.contentInset = contentInsets;
    self.scrollView.scrollIndicatorInsets = contentInsets;
    
    CGRect aRect = self.view.frame;
    aRect.size.height -= kbSize.height;
    if (!CGRectContainsPoint(aRect, activeField.frame.origin) ) {
        [self.scrollView scrollRectToVisible:activeField.frame animated:YES];
    }
}

// From stackoverflow - keyboard will hide
- (void)keyboardWillBeHidden:(NSNotification*)aNotification {
    UIEdgeInsets contentInsets = UIEdgeInsetsZero;
    self.scrollView.contentInset = contentInsets;
    self.scrollView.scrollIndicatorInsets = contentInsets;
}
// From stackoverflow - keyboard is active
- (void)textFieldDidBeginEditing:(UITextField *)textField {
    activeField = textField;
}
// From stackoverflow -  keyboard is done
- (void)textFieldDidEndEditing:(UITextField *)textField {
    activeField = nil;
}
// Enable save button IF all fields are filled
- (void) enableSaveButtonForService:(NSString*) service
                            address:(NSString*) address
                               city:(NSString*) city
                            zipcode:(NSString*) zipcode {
    self.saveButton.enabled = (service.length > 0 && address.length > 0 && city.length > 0 && zipcode.length > 0);
}
// TextFieldDelgate method
- (BOOL) textField: (UITextField* ) textField shouldChangeCharactersInRange: (NSRange) range
 replacementString: (NSString* ) string {
    
    [self addSelectors];
    
    return YES;
}
// TextViewDelegate method
-(BOOL) textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text {
    
    NSString* changedString = [textView.text stringByReplacingCharactersInRange:range withString:text];
    
    [self enableSaveButtonForService: changedString
                             address: self.addressTextField.text
                                city: self.cityTextField.text
                             zipcode: self.zipcodeTextField.text];
    
    return YES;
}

// Hide keyboard
-(BOOL) textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    textField.text = nil;
    return YES;
}
// Hide keyboard based on user touch
-(void) touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
    UITouch* touch = [touches anyObject];
    if(touch.phase == UITouchPhaseBegan) {
        [self.addDescriptionTextView resignFirstResponder];
        [self.addressTextField resignFirstResponder];
        [self.cityTextField resignFirstResponder];
        [self.zipcodeTextField resignFirstResponder];
    }
}
// Selector for if textfields aren't empty, enable save button
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
// Add selector to textfields to check if they're empty or not
-(void) addSelectors {
    [self.addressTextField addTarget:self action:@selector(textFieldChanged:) forControlEvents:UIControlEventEditingChanged];
    [self.cityTextField addTarget:self action:@selector(textFieldChanged:) forControlEvents:UIControlEventEditingChanged];
    [self.zipcodeTextField addTarget:self action:@selector(textFieldChanged:) forControlEvents:UIControlEventEditingChanged];
}

// Back button clicked
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
// Save button clicked, write to database, add to PostsModel
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
                  self.zipcodeTextField.text, @"Zipcode",
                  [[FIRAuth auth]currentUser].email, @"User", nil];
    FIRDatabaseReference* ref = [self.fdbRef childByAutoId];
    [ref setValue:dictionary];
    NSString* currUser = [[FIRAuth auth]currentUser].email;
    NSLog(@"%@ added something!", currUser);
    
    Post* newPost = [[Post alloc] initWithPostDescription:self.addDescriptionTextView.text address:self.addressTextField.text city:self.cityTextField.text zipcode:self.zipcodeTextField.text user: [[FIRAuth auth]currentUser].email];
    [self.addPostsModel addPost:newPost];
    
    self.addSegmentControl.selectedSegmentIndex = 0;
    self.addDescriptionTextView.text = nil;
    self.addressTextField.text = nil;
    self.cityTextField.text = nil;
    self.zipcodeTextField.text = nil;
    [self performSegueWithIdentifier:@"return_feed" sender:self];
}

@end
