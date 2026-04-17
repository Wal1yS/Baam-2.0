import { useState } from 'react';
import { Button, Input, InputNumber, List, Space, Switch, Typography } from 'antd';
import AppShell from '../components/AppShell';

const scannedStudentsDemo = [
    'b.babkov@innopolis.university',
    's.somov@innopolis.university',
    'n.niktov@innopolis.university',
];

export default function Dashboard() {
    const [showCreateSession, setShowCreateSession] = useState(false);
    const [sessionCreated, setSessionCreated] = useState(false);

    const [sessionTitle, setSessionTitle] = useState('');
    const [useGeolocation, setUseGeolocation] = useState(true);
    const [allowedRadius, setAllowedRadius] = useState(50);
    const [sessionDuration, setSessionDuration] = useState(15);

    function handleCreateSession() {
        setSessionCreated(true);
    }

    return (
        <AppShell title="Dashboard">
            <div className="dashboard-content">
                {!showCreateSession ? (
                    <Space direction="vertical" size="middle" style={{ width: '100%' }}>
                        <Typography.Text>
                            Create and configure a new attendance session.
                        </Typography.Text>

                        <Button
                            type="primary"
                            size="large"
                            className="session-primary-btn"
                            onClick={() => setShowCreateSession(true)}
                        >
                            Create Session
                        </Button>
                    </Space>
                ) : (
                    <div className="session-builder">
                        <div className="session-form-block">
                            <h2 className="session-section-title">Session Settings</h2>

                            <div className="session-field">
                                <label className="session-label">Session Title</label>
                                <Input
                                    placeholder="For example: Physics Lecture"
                                    value={sessionTitle}
                                    onChange={(event) => setSessionTitle(event.target.value)}
                                />
                            </div>

                            <div className="session-field session-field-row">
                                <div>
                                    <label className="session-label">Use Geolocation</label>
                                    <p className="session-help-text">
                                        Enable location check for attendance.
                                    </p>
                                </div>
                                <Switch
                                    checked={useGeolocation}
                                    onChange={(checked) => setUseGeolocation(checked)}
                                />
                            </div>

                            <div className="session-field">
                                <label className="session-label">Allowed Radius (meters)</label>
                                <InputNumber
                                    min={1}
                                    max={1000}
                                    value={allowedRadius}
                                    onChange={(value) => setAllowedRadius(value ?? 100)}
                                    style={{ width: '100%' }}
                                />
                            </div>

                            <div className="session-field">
                                <label className="session-label">Session Duration (minutes)</label>
                                <InputNumber
                                    min={1}
                                    max={180}
                                    value={sessionDuration}
                                    onChange={(value) => setSessionDuration(value ?? 15)}
                                    style={{ width: '100%' }}
                                />
                            </div>


                            <div className="session-actions">
                                <Button
                                    type="primary"
                                    size="large"
                                    className="session-primary-btn"
                                    onClick={handleCreateSession}
                                    disabled={!sessionTitle.trim()}
                                >
                                    Generate QR
                                </Button>

                                <Button
                                    size="large"
                                    onClick={() => {
                                        setShowCreateSession(false);
                                        setSessionCreated(false);
                                    }}
                                >
                                    Cancel
                                </Button>
                            </div>
                        </div>

                        {sessionCreated ? (
                            <div className="session-result-grid">
                                <div className="session-result-card">
                                    <h2 className="session-section-title">QR Code</h2>

                                    <div className="qr-placeholder">
                                        <div className="qr-placeholder-inner">
                                            <p className="qr-placeholder-title">QR</p>

                                        </div>
                                    </div>

                                    <div className="session-summary">
                                        <p><strong>Title:</strong> {sessionTitle}</p>
                                        <p><strong>Geolocation:</strong> {useGeolocation ? 'Enabled' : 'Disabled'}</p>
                                        <p><strong>Radius:</strong> {allowedRadius} m</p>
                                        <p><strong>Duration:</strong> {sessionDuration} min</p>
                                    </div>
                                </div>

                                <div className="session-result-card">
                                    <h2 className="session-section-title">Scanned Students</h2>

                                    <List
                                        bordered
                                        dataSource={scannedStudentsDemo}
                                        locale={{ emptyText: 'No students scanned yet' }}
                                        renderItem={(email) => <List.Item>{email}</List.Item>}
                                    />
                                </div>
                            </div>
                        ) : null}
                    </div>
                )}
            </div>
        </AppShell>
    );
}