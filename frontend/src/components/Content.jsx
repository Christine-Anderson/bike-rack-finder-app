import { Flex, Container, VStack, Divider, Text, Button, Input, IconButton } from "@chakra-ui/react";
import { SearchIcon } from "@chakra-ui/icons";
import BikeRackCard from "./BikeRackCard";
import ReportModal from "./ReportModal";

// todo break into sub Components
const Content = () => {
    return (
        <Container height="calc(100vh - 8rem)" maxW="80vw" padding="4" mt={5} mb={5}>
            <Flex direction="column" height="100%">
                <Flex flex="1" direction={{ base: "column", md: "row" }} padding="4" overflowY="auto">
                    <VStack spacing={4} p={4} w="20rem" overflowY="auto" borderRight="1px solid #E2E8F0">
                        <Text fontSize="xl" fontWeight="bold">Bike Racks</Text>
                        <Divider />
                        <BikeRackCard />
                        <BikeRackCard />
                        <BikeRackCard />
                        <BikeRackCard />
                        <BikeRackCard />
                        <BikeRackCard />
                        <BikeRackCard />
                    </VStack>

                    {/* Google Maps */}
                    <Flex direction="column" flex="1">
                        {/* Search Bar */}
                        <Flex alignItems="center" justifyContent="center" m={3}>
                            <Input placeholder="Search..." variant="filled" size="md" mr={3} />
                            <IconButton
                                colorScheme="blue"
                                aria-label="Search database"
                                icon={<SearchIcon />}
                            />
                        </Flex>

                        {/* Map */}
                        <Flex flex="1" bg="green" mb={4}>
                            Google Maps here
                        </Flex>

                        <Flex alignItems="center" justifyContent="space-between">
                            <Button colorScheme="blue" left="50%" transform="translateX(-50%)">Find Closest Rack</Button>
                            <ReportModal
                                buttonText="Report New Rack"
                                buttonSize={"md"}
                                buttonRight={"0"}
                                headerText="Report New Rack"
                                bodyText="Are you sure you want to report a new rack at 123 Fake Street, City?"
                            />
                        </Flex>
                    </Flex>
                </Flex>
            </Flex>
        </Container>
    );
};

export default Content;