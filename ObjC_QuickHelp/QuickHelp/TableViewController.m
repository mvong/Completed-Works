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
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.tablePostsModel getNumPosts];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.textLabel.text = [self.tablePostsModel getPostAtIndex:indexPath.row].postDescription;
    cell.detailTextLabel.text = [self.tablePostsModel getPostAtIndex:indexPath.row].user;
    
    return cell;
}


-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [self performSegueWithIdentifier:@"show_info" sender:tableView];
}

#pragma mark - Navigation

// Prepare to segue for more info
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([segue.identifier isEqualToString:@"show_info"]) {
        UINavigationController* nc = (UINavigationController*)segue.destinationViewController;
        ServiceViewController* svc = [[nc viewControllers] lastObject];
        svc.authorLabel.text = @"Please";
    }
}

// Log out if user clicks logout button
- (IBAction)logoutClicked:(id)sender {
    NSError *signOutError;
    BOOL status = [[FIRAuth auth] signOut:&signOutError];
    if (!status) {
        [self showAlertWithError:signOutError];
    } else {
        [self performSegueWithIdentifier:@"logout_success" sender:self];
    }
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




@end
