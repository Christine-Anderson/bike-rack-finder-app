import { Card, CardHeader, CardBody, CardFooter, Text, Heading, Flex } from "@chakra-ui/react";
import StarRating from "./StarRating";
import ReportModal from "./ReportModal";
import RatingModal from "./RatingModal";

const BikeRackCard = () => {
    return (
        <Card align="center" width="100%" size="sm">
            <CardHeader>
                <Heading size="sm"> 123 Fake Street, City </Heading>
            </CardHeader>
            <CardBody>
                <Flex direction="column" gap={4}>
                    <Flex justify="space-between" align="center" width="100%">
                        <Text size="sm" mr={10}>Thefts: 5</Text>
                        <ReportModal
                            buttonText="Report Theft"
                            buttonSize={"sm"}
                            buttonRight={"auto"}
                            headerText="Report Theft"
                            bodyText="Are you sure you want to report theft at 123 Fake Street, City?"
                        />
                    </Flex>
                    <Flex justify="space-between" align="center" width="100%">
                        <StarRating rating={3.5}/>
                        <RatingModal/>
                    </Flex>
                </Flex>
            </CardBody>
            <CardFooter>
                        <ReportModal
                            buttonText="Report Removal"
                            buttonSize={"sm"}
                            buttonRight={"auto"}
                            headerText="Report Removal"
                            bodyText="Are you sure you want to report removal at 123 Fake Street, City?"
                        />
            </CardFooter>
        </Card>
    );
};

export default BikeRackCard;