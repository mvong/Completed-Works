//
//  ServiceViewController.m
//  QuickHelp
//
//  Created by Mark on 12/5/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import "ServiceViewController.h"

@interface ServiceViewController () <CLLocationManagerDelegate>

@property (weak, nonatomic) IBOutlet MKMapView *mapView;
@property (strong, nonatomic) CLLocation *startPoint;
@property (assign, nonatomic) CLLocationDistance distanceFromStart;
@property (strong, nonatomic) CLLocationManager* locationManager;

@end

@implementation ServiceViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self setUpLocationManager];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

// Helper function that sets up locationManager
- (void) setUpLocationManager {
    self.locationManager = [[CLLocationManager alloc] init];
    self.locationManager.delegate = self;
    self.locationManager.desiredAccuracy = kCLLocationAccuracyBest;
    self.locationManager.distanceFilter = kCLDistanceFilterNone;
    [self.locationManager requestAlwaysAuthorization];
    [self.locationManager startUpdatingLocation];
    self.mapView.showsUserLocation = YES;
}
// Sets up the map locator based on user's location
- (void)locationManager:(CLLocationManager *)manager
     didUpdateLocations:(NSArray *)locations {
    CLLocation *newLocation = [locations lastObject];
    if (self.startPoint == nil) {
        self.startPoint = newLocation;
        self.distanceFromStart = 0;
        XYZMarker *start = [[XYZMarker alloc] init];
        start.coordinate = newLocation.coordinate;
        start.title = @"Start Point";
        start.subtitle = @"This is where we started!";
        [self.mapView addAnnotation:start];
        MKCoordinateRegion region = MKCoordinateRegionMakeWithDistance
        (newLocation.coordinate, 100, 100);
        [self.mapView setRegion:region animated:YES];
        NSLog(@"Entered");
    } else {
        self.distanceFromStart =
        [newLocation distanceFromLocation:self.startPoint];
    }
}
// LocationManager failed...most likely because user denied permission
-(void)locationManager:(CLLocationManager *)manager
      didFailWithError:(NSError *)error {
    NSLog(@"Error: %@", error);
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
