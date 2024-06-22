import { Card, CardHeader, CardBody, CardFooter, Text, Button, Heading, Flex } from "@chakra-ui/react";
import StarRating from "./StarRating";

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
                        <Button size="sm">Report Theft</Button>
                    </Flex>
                    <Flex justify="space-between" align="center" width="100%">
                        <StarRating rating={3.5}/>
                        <Button size="sm">Rate</Button>
                    </Flex>
                </Flex>
            </CardBody>
            <CardFooter>
                <Button size="sm">Report Removal</Button>
            </CardFooter>
        </Card>
    );
};

export default BikeRackCard;