import type { IncidentComment } from "../models/IncidentComment";
import http from "./http";

export async function getComments(incidentId: string) {
    const { data } = await http.get(`/incidents/${incidentId}/comments`);
    return data as IncidentComment[]; 
}

export async function addComment(incidentId: string, comment: string) {
    const { data } = await http.post(`/incidents/${incidentId}/comments`, { comment })
    return data as IncidentComment;
}