//
//  ServiceViewController.h
//  QuickHelp
//
//  Created by Mark on 12/5/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PostsModel.h"
#import "ServiceNavigationController.h"
#import <CoreLocation/CoreLocation.h>
#import <MapKit/MapKit.h>
#import "XYZMarker.h"

typedef void (^PostAdditionHandler)(NSMutableArray* posts);

@interface ServiceViewController : UIViewController

@property (weak, nonatomic) IBOutlet UILabel *authorLabel;
@property (weak, nonatomic) IBOutlet UILabel *locationLabel;
@property (weak, nonatomic) IBOutlet UITextView *serviceTextView;
@property (copy, nonatomic) PostAdditionHandler postHandler;

@end
