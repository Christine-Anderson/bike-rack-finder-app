import React, { useState, useEffect } from "react";
import { Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton } from "@chakra-ui/react";
import { Button, Text, FormControl, FormLabel, Textarea, useDisclosure, useToast } from "@chakra-ui/react";
import { useKeycloak } from "@react-keycloak/web";
import { useMutation } from "@tanstack/react-query";

import submitReport from "../queries/submitReport";

const ReportModal = ({ rackId, reportType, address, buttonSize, buttonRight }) => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const {keycloak} = useKeycloak();
    const toast = useToast();
    const theftReportMutation = useMutation(submitReport);
    const removalReportMutation = useMutation(submitReport);

    let [value, setValue] = React.useState("");
    const [isLoading, setIsLoading] = useState(false);

    let handleInputChange = (ev) => {
        let inputValue = ev.target.value
        setValue(inputValue)
    }

    const handleOpen = () => {
        if (keycloak.authenticated) {
            onOpen();
        } else {
            toast({
                title: "Login to Access",
                description: "Please login to submit a report.",
                status: "info",
                duration: 5000,
                isClosable: true,
            })
        }
    }

    const handleSubmit = () => {
        const userId = keycloak.tokenParsed.sub;
        const accessToken = keycloak.token;
        setIsLoading(true);

        const report = {
            rackId,
            details: value,
            userId,
            accessToken
        }

        if (reportType === "Theft") {
            theftReportMutation.mutate({
                ...report,
                reportType: "THEFT"
            });
        } else if (reportType === "Removal") {
            removalReportMutation.mutate({
                ...report,
                reportType: "REMOVED_RACK"
            });
        }
    }

    useEffect(() => {
        if (theftReportMutation.isError || removalReportMutation.isError) {
            toast({
                title: "Error Submitting Report",
                status: "error",
                duration: 5000,
                isClosable: true,
            });
            setIsLoading(false);
        } else if (theftReportMutation.isSuccess || removalReportMutation.isSuccess) {
            console.log("submit report: " + rackId + ", " + value);
            onClose();
            setIsLoading(false);
        }
    }, [theftReportMutation.isError, theftReportMutation.isSuccess, removalReportMutation.isError, removalReportMutation.isSuccess])

    return (
        <>
            <Button size={buttonSize} right={buttonRight} onClick={handleOpen}>
                {`Report ${reportType}`}
            </Button>

            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>
                        {`Report ${reportType}`}
                    </ModalHeader>
                    <ModalCloseButton />
                    <ModalBody pb={6}>
                        <FormControl>
                            <Text align="center" mb={6}>
                                {`Are you sure you want to report ${reportType.toLowerCase()} at ${address}?`}
                            </Text>
                            <FormLabel>Details</FormLabel>
                            <Textarea
                                value={value}
                                onChange={handleInputChange}
                                placeholder="Enter additional details here"
                                size="sm"
                                maxLength={255}
                            />
                        </FormControl>
                </ModalBody>
                    <ModalFooter>
                        <Button 
                            isLoading={isLoading}
                            loadingText="Submitting"
                            colorScheme="blue"
                            mr={6}
                            onClick={handleSubmit}
                        >
                            Submit
                        </Button>
                        <Button onClick={onClose}>Cancel</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default ReportModal;