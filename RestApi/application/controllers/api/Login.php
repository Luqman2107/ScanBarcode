<?php defined('BASEPATH') OR exit('No direct script access allowed');

use Restserver\Libraries\REST_Controller;

require APPPATH . '/libraries/REST_Controller.php';

class Login extends REST_Controller {
    public function __construct() {
        parent::__construct();
        // Load Model
        $this->load->model('DataModel', 'data');
    }

    public function index_post()
    {
        $this->form_validation->set_rules('userMail', 'userMail', 'trim|required');
        $this->form_validation->set_rules('userPassword', 'userPassword', 'trim|required|max_length[100]');
        $this->form_validation->set_rules('token', 'token', 'trim|required');
        if ($this->form_validation->run() == false) {
            $message = array(
                'status' => false,
                'error' => $this->form_validation->error_array(),
                'message' => validation_errors()
            );
            $this->response($message, REST_Controller::HTTP_OK);
        } else {
            $output = $this->data->user_login($this->input->post('userMail'), $this->input->post('userPassword'));
                if (!empty($output) and $output != FALSE) {
                    if ($output->status == 0) {
                        $message = [
                            'status' => false,
                            'data' => "Your account is not active",
                        ];
                        $this->response($message, REST_Controller::HTTP_OK);
                    } else {
                        $id =  $output->userID ;

                        $this->db->set('token', $this->input->post('token'));
                        $this->db->where('userID ', $id);
                        $this->db->update('data_user');
                        //load token
                        $this->load->library('Authorization_Token');
                        //generate
                        $token_data['userID'] = $output->userID ;
                        $token_data['userName'] = $output->userName;
                        $token_data['userNIK'] = $output->userNIK;
                        $token_data['userMail'] = $output->userMail;
                        $token_data['token'] = $this->input->post('token');
                        $token_data['status'] = $output->status;
                        $token_data['CreateBy'] = $output->CreateBy;
                        $token_data['CreateDate'] = $output->CreateDate;
                        $token_data['LastModifyBy'] = $output->LastModifyBy;
                        $token_data['LastModifyDate'] = $output->LastModifyDate;

                        $return_data = [
                            'userID' => $output->userID ,
                            'userName' => $output->userName,
                            'userNIK' => $output->userNIK,
                            'userMail' => $output->userMail,
                            'token' => $this->input->post('token'),
                            'status' => $output->status,
                            'CreateBy' => $output->CreateBy,
                            'CreateDate' => $output->CreateDate,
                            'LastModifyBy' => $output->LastModifyBy,
                            'LastModifyDate' => $output->LastModifyDate,
                            'pesan' => 'Login Success',
                        ];
                        $message = [
                            'status' => true,
                            'data' => $return_data,
                        ];
                        $this->response($message, REST_Controller::HTTP_OK);
                    }
                } else {
                    $message = [
                    'status' => false,
                    'pesan' => "Invalid Username / Password"
                    ];
                    $this->response($message, REST_Controller::HTTP_NOT_FOUND);
            }
        }
    }
} 