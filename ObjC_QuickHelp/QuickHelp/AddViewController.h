//
//  AddViewController.h
//  QuickHelp
//
//  Created by Mark on 12/5/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AddNavigationController.h"
#import "PostsModel.h"
@interface AddViewController : UIViewController
typedef void (^AddServiceCompletionHandler)(NSUInteger segment,
                                            NSString* description,
                                            NSString* address,
                                            NSString* city,
                                            NSString* zipcode);
@property (weak, nonatomic) IBOutlet UISegmentedControl *addSegmentControl;
@property (weak, nonatomic) IBOutlet UITextView *addDescriptionTextView;
@property (weak, nonatomic) IBOutlet UITextField *addressTextField;
@property (weak, nonatomic) IBOutlet UITextField *cityTextField;
@property (weak, nonatomic) IBOutlet UITextField *zipcodeTextField;
@property (copy, nonatomic) AddServiceCompletionHandler AddService;
@end
