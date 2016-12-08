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
@property (strong, nonatomic) PostsModel* servicePostsModel;

@end

@implementation ServiceViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.servicePostsModel = [PostsModel sharedPostsModel];
    [self setUpLocationManager];
    [self setUpInterface];
}

- (void) setUpInterface {
    self.authorLabel.text = [NSString stringWithFormat:@"Author: %@", [self.servicePostsModel getPostAtIndex:self.servicePostsModel.currentPost].user];
    self.locationLabel.text = [NSString stringWithFormat:@"Location: %@, %@, %@", [self.servicePostsModel getPostAtIndex:self.servicePostsModel.currentPost].address, [self.servicePostsModel getPostAtIndex:self.servicePostsModel.currentPost].city, [self.servicePostsModel getPostAtIndex:self.servicePostsModel.currentPost].zipcode];
    self.serviceTextView.text = [NSString stringWithFormat:@"Description\n%@", [self.servicePostsModel getPostAtIndex:self.servicePostsModel.currentPost].postDescription];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

// Helper function that sets up locationManager && identifies nearby services
- (void) setUpLocationManager {
    CLGeocoder* geoCoder = [[CLGeocoder alloc] init];
    for(int i = 0; i < [self.servicePostsModel getNumPosts]; i++) {
        NSString* address = [self.servicePostsModel getPostAtIndex:i].address;
        NSString* city = [self.servicePostsModel getPostAtIndex:i].city;
        NSString* zipcode = [self.servicePostsModel getPostAtIndex:i].zipcode;
        NSString* description = [self.servicePostsModel getPostAtIndex:i].postDescription;
        NSString* fullAddress = [NSString stringWithFormat:@"%@, %@, %@", address, city, zipcode];
        [geoCoder geocodeAddressString:fullAddress completionHandler:^(NSArray<CLPlacemark *> * _Nullable placemarks, NSError * _Nullable error) {
            if(error) {
                NSLog(@"%@", error);
            } else {
                CLPlacemark* placemark = [placemarks lastObject];
                XYZMarker* serviceMarker = [[XYZMarker alloc] init];
                CLLocation* spotLocation = [[CLLocation alloc] initWithLatitude:placemark.location.coordinate.latitude
                                                                     longitude:placemark.location.coordinate.longitude];
                serviceMarker.coordinate = spotLocation.coordinate;
                serviceMarker.title = @"Service!";
                serviceMarker.subtitle = description;
                [self.mapView addAnnotation:serviceMarker];
                MKCoordinateRegion region = MKCoordinateRegionMakeWithDistance
                (spotLocation.coordinate, 100, 100);
                [self.mapView setRegion:region animated:YES];
            }
        }];
        
    }
    self.locationManager = [[CLLocationManager alloc] init];
    self.locationManager.delegate = self;
    self.locationManager.desiredAccuracy = kCLLocationAccuracyBest;
    self.locationManager.distanceFilter = kCLDistanceFilterNone;
    [self.locationManager requestAlwaysAuthorization];
    [self.locationManager startUpdatingLocation];
    self.mapView.showsUserLocation = YES;
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
