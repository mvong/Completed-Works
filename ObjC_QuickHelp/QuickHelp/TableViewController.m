//
//  TableViewController.m
//  QuickHelp
//
//  Created by Mark on 12/5/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import "TableViewController.h"
@import Firebase;
@interface TableViewController ()

@property (strong, nonatomic) PostsModel* tablePostsModel;

@end

@implementation TableViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.tablePostsModel = [PostsModel sharedPostsModel];
    [self.tableView reloadData];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

// Number of sections
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

// Set number of cells
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.tablePostsModel getNumPosts];
}

// Initialize cells
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.textLabel.text = [self.tablePostsModel getPostAtIndex:indexPath.row].postDescription;
    cell.detailTextLabel.text = [self.tablePostsModel getPostAtIndex:indexPath.row].user;
    
    return cell;
}

// Get selected service row
-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    self.tablePostsModel.currentPost = indexPath.row;
    HistoryPostsModel* historyPostsModel = [HistoryPostsModel sharedHistoryPostsModel];
    NSString* postDesc = [self.tablePostsModel getPostAtIndex:indexPath.row].postDescription;
    NSString* addressStr = [self.tablePostsModel getPostAtIndex:indexPath.row].address;
    NSString* cityStr = [self.tablePostsModel getPostAtIndex:indexPath.row].city;
    NSString* zipcodeStr = [self.tablePostsModel getPostAtIndex:indexPath.row].zipcode;
    NSString* userStr = [self.tablePostsModel getPostAtIndex:indexPath.row].user;
    NSDictionary* newPost = [[NSDictionary alloc] initWithObjectsAndKeys:postDesc, @"Description", addressStr, @"Address", cityStr, @"City", zipcodeStr, @"Zipcode", userStr, @"User", nil];
    [historyPostsModel addPost:newPost];
    [self performSegueWithIdentifier:@"show_info" sender:self];
}


// Log out if user clicks logout button
- (IBAction)logoutClicked:(id)sender {
    [self showMenuOptions];
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
// Show menu of options
-(void) showMenuOptions {
    UIAlertController *alertController = [UIAlertController
                                          alertControllerWithTitle:@"Menu Options!"
                                          message:@"Choose an option."
                                          preferredStyle:UIAlertControllerStyleAlert];
    
    UIAlertAction *historyAction = [UIAlertAction
                                    actionWithTitle:@"History"
                                    style:UIAlertActionStyleCancel
                                    handler:^(UIAlertAction *action)
                                    {
                                        NSLog(@"History clicked");
                                        [self performSegueWithIdentifier:@"history_clicked" sender:self];
                                    }];
    
    UIAlertAction *logoutAction = [UIAlertAction
                                   actionWithTitle:@"Logout"
                                   style:UIAlertActionStyleDefault
                                   handler:^(UIAlertAction *action)
                                   {
                                       NSLog(@"logout clicked");
                                       NSError *signOutError;
                                       BOOL status = [[FIRAuth auth] signOut:&signOutError];
                                       if (!status) {
                                           [self showAlertWithError:signOutError];
                                       } else {
                                           [self performSegueWithIdentifier:@"logout_success" sender:self];
                                       }

                                   }];
    
    UIAlertAction *cancelAction = [UIAlertAction
                               actionWithTitle:@"Cancel"
                               style:UIAlertActionStyleDefault
                               handler:^(UIAlertAction *action)
                               {
                                   NSLog(@"cancel clicked");
                               }];
    
    [alertController addAction:historyAction];
    [alertController addAction:cancelAction];
    [alertController addAction:logoutAction];
    [self presentViewController:alertController animated:YES completion:nil];
}


@end
