//
//  HistoryTableViewController.m
//  QuickHelp
//
//  Created by Mark on 12/8/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import "HistoryTableViewController.h"

@interface HistoryTableViewController()

@property (strong, nonatomic) HistoryPostsModel* historyPostsModel;
@end

@implementation HistoryTableViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.historyPostsModel = [HistoryPostsModel sharedHistoryPostsModel];
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
    return [self.historyPostsModel getNumPosts];
}

// Initialize cells
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.textLabel.text = [self.historyPostsModel getPostAtIndex:indexPath.row][@"Description"];
    cell.detailTextLabel.text = [self.historyPostsModel getPostAtIndex:indexPath.row][@"User"];
    
    return cell;
}


- (IBAction)historyBackClicked:(id)sender {
    [self performSegueWithIdentifier:@"history_back" sender:self];
}

@end
