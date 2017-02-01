//
//  ViewController.h
//  QuickHelp
//
//  Created by Mark on 12/5/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PostsModel.h"
#import "TableViewController.h"

@interface ViewController : UIViewController
@property (strong, nonatomic) PostsModel* postsModel;
@end

