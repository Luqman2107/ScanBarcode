<?php 
 
class DataModel extends CI_Model 
{
  protected $user_table = 'data_user';

  public function insert($tabel, $arr)
  {
    $cek = $this->db->insert($tabel, $arr);
    return $cek;
  }

  public function user_login($userMail, $userPassword)
  {
    $this->db->or_where('userMail', $userMail);
    $q = $this->db->get($this->user_table);
      // $q = $this->db->query("SELECT * FROM pengguna WHERE email = '$email'")->get($this->user_table);
    if ($q->num_rows()) 
    {
      $user_pass = $q->row('userPassword');
      if (md5($userPassword) === $user_pass) {
        return $q->row();
      }
        return FALSE;
    } else {
      return FALSE;
    }
  }
}