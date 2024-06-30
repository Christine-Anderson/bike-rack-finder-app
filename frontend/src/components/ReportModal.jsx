import React, { useState, useEffect } from "react";
import { Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton } from "@chakra-ui/react";
import { Button, Text, FormControl, FormLabel, Textarea, useDisclosure, useToast } from "@chakra-ui/react";
import { useKeycloak } from "@react-keycloak/web";
import { useMutation } from "@tanstack/react-query";

import submitTheftReport from "../queries/submitTheftReport";

const ReportModal = ({ rackId, reportType, address, buttonSize, buttonRight }) => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const {keycloak} = useKeycloak();
    const toast = useToast();
    const theftReportMutation = useMutation(submitTheftReport);

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

    const onClickSubmit = () => {
        const userId = keycloak.tokenParsed.sub;
        console.log("submit: " + rackId + ", " + reportType + ", " + value + ", " + userId);
        const accessToken = keycloak.token;
        setIsLoading(true);
        theftReportMutation.mutate({
            rackId: rackId,
            reportType: "THEFT",
            details: value,
            userId: userId, 
            accessToken: accessToken
        });
    }

    useEffect(() => {
        if (theftReportMutation.isError) {
            toast({
                title: "Error Submitting Theft Report",
                status: "error",
                duration: 5000,
                isClosable: true,
            });
            setIsLoading(false);
        } else if (theftReportMutation.isSuccess) {
            console.log("submit theft report: " + rackId + ", " + value);
            onClose();
            setIsLoading(false);
        }
    }, [theftReportMutation.isError, theftReportMutation.isSuccess])

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
                            onClick={onClickSubmit}
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