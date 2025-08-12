export type Incident = {
    id: string;
    title: string;
    description?: string;
    status: 'OPEN' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED';
    priority: 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
    reportedBy: string;
    assignedTo?: string;
    createdAt?: string;
    updatedAt?: string;
}